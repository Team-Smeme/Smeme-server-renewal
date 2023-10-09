package com.smeme.server.service.auth;

import com.smeme.server.config.jwt.JwtTokenProvider;
import com.smeme.server.config.jwt.UserAuthentication;
import com.smeme.server.dto.auth.SignInRequestDTO;
import com.smeme.server.dto.auth.SignInResponseDTO;
import com.smeme.server.dto.auth.token.TokenResponseDTO;
import com.smeme.server.dto.auth.token.TokenVO;
import com.smeme.server.model.LangType;
import com.smeme.server.model.Member;
import com.smeme.server.model.SocialType;
import com.smeme.server.repository.MemberRepository;
import com.smeme.server.service.CorrectionService;
import com.smeme.server.service.DiaryService;
import com.smeme.server.service.MemberBadgeService;
import com.smeme.server.service.TrainingTimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static com.smeme.server.util.message.ErrorMessage.*;
import static java.util.Objects.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private static final Long ACCESS_TOKEN_EXPIRATION_TIME = 60 * 60 * 1000 * 2 * 12 * 100L; // 2시간

    private static final Long REFRESH_TOKEN_EXPIRATION_TIME = 60 * 60 * 1000 * 24 * 14L; // 2주


    private final JwtTokenProvider jwtTokenProvider;

    private final MemberRepository memberRepository;

    private final AppleSignInService appleSignInService;
    private final KakaoSignInService kakaoSignInService;
    private final MemberBadgeService memberBadgeService;
    private final DiaryService diaryService;
    private final TrainingTimeService trainingTimeService;
    private final CorrectionService correctionService;

    @Transactional
    public SignInResponseDTO signIn(String socialAccessToken, SignInRequestDTO request) throws NoSuchAlgorithmException, InvalidKeySpecException {

        SocialType socialType = request.socialType();
        String socialId = login(socialType, socialAccessToken);

        boolean hasMember = isMemberBySocialAndSocialId(socialType, socialId);

        if (!hasMember) {
            Member member = Member.builder()
                    .social(socialType)
                    .socialId(socialId)
                    .targetLang(LangType.en)
                    .fcmToken(request.fcmToken())
                    .build();
            memberRepository.save(member);
        }

        Member signedMember = getMemberBySocialAndSocialId(socialType, socialId);
        boolean isRegistered = nonNull(signedMember.getUsername());
        boolean hasPlan = nonNull(signedMember.getGoal());

        TokenVO tokenVO = generateToken(new UserAuthentication(signedMember.getId(), null, null));
        signedMember.updateRefreshToken(tokenVO.refreshToken());

        return SignInResponseDTO.builder()
                .accessToken(tokenVO.accessToken())
                .refreshToken(tokenVO.refreshToken())
                .isRegistered(isRegistered)
                .hasPlan(hasPlan)
                .build();
    }

    @Transactional
    public TokenResponseDTO issueToken(Long memberId) {

        TokenVO tokenVO = generateToken(new UserAuthentication(memberId, null, null));

        Member member = getMemberById(memberId);
        member.updateRefreshToken(tokenVO.refreshToken());

        return TokenResponseDTO.builder()
                .accessToken(tokenVO.accessToken())
                .refreshToken(tokenVO.refreshToken())
                .build();
    }

    @Transactional
    public void signOut(Long memberId) {
        Member member = getMemberById(memberId);
        member.updateRefreshToken(null);
    }

    @Transactional
    public void withdraw(Long memberId) {
        diaryService.getAllByMemberId(memberId).forEach(diary -> correctionService.deleteAllByDiaryId(diary.getId()));
        diaryService.deleteAllByMemberId(memberId);
        trainingTimeService.deleteAllByMemberId(memberId);
        memberBadgeService.deleteAllByMemberId(memberId);
        memberRepository.deleteById(memberId);
    }

    private TokenVO generateToken(Authentication authentication) {
        return TokenVO.of(
                jwtTokenProvider.generateToken(authentication, ACCESS_TOKEN_EXPIRATION_TIME),
                jwtTokenProvider.generateToken(authentication, REFRESH_TOKEN_EXPIRATION_TIME));
    }


    private Member getMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException(INVALID_MEMBER.getMessage()));
    }

    private Member getMemberBySocialAndSocialId(SocialType socialType, String socialId) {
        return memberRepository.findBySocialAndSocialId(socialType, socialId)
                .orElseThrow(() -> new RuntimeException(INVALID_MEMBER.getMessage()));
    }

    private boolean isMemberBySocialAndSocialId(SocialType socialType, String socialId) {
        return memberRepository.existsBySocialAndSocialId(socialType, socialId);
    }

    private String login(SocialType socialType, String socialAccessToken) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return switch (socialType.toString()) {
            case "APPLE" -> appleSignInService.getAppleData(socialAccessToken);
            case "KAKAO" -> kakaoSignInService.getKakaoData(socialAccessToken);
            default -> throw new InvalidBearerTokenException(INVALID_TOKEN.getMessage());
        };
    }
}

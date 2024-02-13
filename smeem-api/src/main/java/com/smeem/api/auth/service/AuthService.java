package com.smeem.api.auth.service;

import com.smeem.api.auth.controller.dto.request.SignInRequestDTO;
import com.smeem.api.auth.controller.dto.response.SignInResponseDTO;
import com.smeem.api.auth.controller.dto.response.token.TokenResponseDTO;
import com.smeem.api.auth.controller.dto.response.token.TokenVO;
import com.smeem.api.auth.jwt.JwtTokenProvider;


import com.smeem.api.auth.jwt.UserAuthentication;
import com.smeem.api.diary.service.DiaryTransactionalService;
import com.smeem.api.member.service.MemberBadgeService;
import com.smeem.api.member.service.TrainingTimeService;
import com.smeem.common.exception.MemberException;
import com.smeem.common.exception.TokenException;
import com.smeem.domain.member.model.LangType;
import com.smeem.domain.member.model.Member;
import com.smeem.domain.member.model.SocialType;
import com.smeem.domain.member.repository.MemberRepository;
import com.smeem.external.apple.AppleSignInService;
import com.smeem.external.kakao.KakaoSignInService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static com.smeem.common.code.failure.AuthFailureCode.INVALID_TOKEN;
import static com.smeem.common.code.failure.MemberFailureCode.INVALID_MEMBER;
import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private static final Long ACCESS_TOKEN_EXPIRATION_TIME = 60 * 60 * 1000 * 2 * 12 * 1000000L; // 2시간

    private static final Long REFRESH_TOKEN_EXPIRATION_TIME = 60 * 60 * 1000 * 24 * 14L; // 2주


    private final JwtTokenProvider jwtTokenProvider;

    private final MemberRepository memberRepository;

    private final AppleSignInService appleSignInService;
    private final KakaoSignInService kakaoSignInService;
    private final MemberBadgeService memberBadgeService;
    private final DiaryTransactionalService diaryService;
    private final TrainingTimeService trainingTimeService;

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

        return SignInResponseDTO.of(tokenVO, isRegistered, hasPlan);
    }

    @Transactional
    public TokenResponseDTO issueToken(Long memberId) {

        TokenVO tokenVO = generateToken(new UserAuthentication(memberId, null, null));

        Member member = get(memberId);
        member.updateRefreshToken(tokenVO.refreshToken());

        return TokenResponseDTO.builder()
                .accessToken(tokenVO.accessToken())
                .refreshToken(tokenVO.refreshToken())
                .build();
    }

    @Transactional
    public void signOut(Long memberId) {
        Member member = get(memberId);
        member.updateRefreshToken(null);
    }

    @Transactional
    public void withdraw(Long memberId) {
        Member member = get(memberId);
        diaryService.deleteAllByMember(member);
        trainingTimeService.deleteAllByMember(member);
        memberBadgeService.deleteAllByMember(member);
        memberRepository.deleteById(memberId);
    }

    private TokenVO generateToken(Authentication authentication) {
        return TokenVO.of(
                jwtTokenProvider.generateToken(authentication, ACCESS_TOKEN_EXPIRATION_TIME),
                jwtTokenProvider.generateToken(authentication, REFRESH_TOKEN_EXPIRATION_TIME));
    }


    private Member get(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(INVALID_MEMBER));
    }

    private Member getMemberBySocialAndSocialId(SocialType socialType, String socialId) {
        return memberRepository.findBySocialAndSocialId(socialType, socialId)
                .orElseThrow(() -> new MemberException(INVALID_MEMBER));
    }

    private boolean isMemberBySocialAndSocialId(SocialType socialType, String socialId) {
        return memberRepository.existsBySocialAndSocialId(socialType, socialId);
    }

    // TODO : Custom Exception 처리
    private String login(SocialType socialType, String socialAccessToken) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return switch (socialType.toString()) {
            case "APPLE" -> appleSignInService.getAppleData(socialAccessToken);
            case "KAKAO" -> kakaoSignInService.getKakaoData(socialAccessToken);

            // TODO : Change to Custom Exception
            default -> throw new TokenException(INVALID_TOKEN);
        };
    }
}

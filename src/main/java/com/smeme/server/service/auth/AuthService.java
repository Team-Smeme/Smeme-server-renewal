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
import com.smeme.server.model.badge.Badge;
import com.smeme.server.model.badge.MemberBadge;
import com.smeme.server.repository.MemberRepository;
import com.smeme.server.repository.badge.BadgeRepository;
import com.smeme.server.repository.badge.MemberBadgeRepository;
import com.smeme.server.util.message.ErrorMessage;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${badge.welcome-badge-id}")
    private Long WELCOME_BADGE_ID;

    private final JwtTokenProvider jwtTokenProvider;

    private final MemberRepository memberRepository;

    private final AppleSignInService appleSignInService;

    private final KakaoSignInService kakaoSignInService;
    private final MemberBadgeRepository memberBadgeRepository;
    private final BadgeRepository badgeRepository;

    @Transactional
    public SignInResponseDTO signIn(String socialAccessToken, SignInRequestDTO signInRequestDTO) throws NoSuchAlgorithmException, InvalidKeySpecException {

        SocialType socialType = signInRequestDTO.socialType();
        String socialId = login(signInRequestDTO.socialType(), socialAccessToken);
        boolean isRegistered = isMemberBySocialAndSocialId(socialType, socialId);

        if (!isRegistered) {
            Member member = Member.builder()
                    .social(socialType)
                    .socialId(socialId)
                    .targetLang(LangType.en)
                    .build();
            memberRepository.save(member);
        }

        Member signedMember = getMemberBySocialAndSocialId(socialType, socialId);

        boolean hasPlan = !isNull(signedMember.getGoal());

        TokenVO tokenVO = generateToken(new UserAuthentication(signedMember.getId(), null, null));

        signedMember.updateRefreshToken(tokenVO.refreshToken());
        addWelcomeBadge(signedMember);

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

    private TokenVO generateToken(Authentication authentication) {
        return TokenVO.of(
                jwtTokenProvider.generateToken(authentication, ACCESS_TOKEN_EXPIRATION_TIME),
                jwtTokenProvider.generateToken(authentication, REFRESH_TOKEN_EXPIRATION_TIME));
    }

    private void addWelcomeBadge(Member member) {
         Badge badge = badgeRepository.findById(WELCOME_BADGE_ID).orElseThrow(
                 () -> new EntityNotFoundException(ErrorMessage.EMPTY_BADGE.getMessage())
         );
        memberBadgeRepository.save(new MemberBadge(member, badge));
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

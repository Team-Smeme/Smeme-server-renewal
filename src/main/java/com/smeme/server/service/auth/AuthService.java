package com.smeme.server.service.auth;

import com.smeme.server.config.jwt.JwtTokenProvider;
import com.smeme.server.config.jwt.UserAuthentication;
import com.smeme.server.dto.auth.SignInRequestDTO;
import com.smeme.server.dto.auth.SignInResponseDTO;
import com.smeme.server.dto.auth.token.TokenResponseDTO;
import com.smeme.server.model.LangType;
import com.smeme.server.model.Member;
import com.smeme.server.model.SocialType;
import com.smeme.server.repository.MemberRepository;
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
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;

    private final MemberRepository memberRepository;

    private final AppleSignInService appleSignInService;

    private final KakaoSignInService kakaoSignInService;

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

        Authentication authentication = new UserAuthentication(signedMember.getId(), null, null);

        String refreshToken = jwtTokenProvider.generateRefreshToken(authentication);
        String accessToken = jwtTokenProvider.generateAccessToken(authentication);

        return SignInResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .isRegistered(isRegistered)
                .hasPlan(hasPlan)
                .build();
    }

    @Transactional
    public TokenResponseDTO issueToken(Long memberId) {

        Authentication authentication = new UserAuthentication(memberId, null, null);

        String newAccessToken = jwtTokenProvider.generateAccessToken(authentication);
        String newRefreshToken = jwtTokenProvider.generateRefreshToken(authentication);

        Member member = getMemberById(memberId);
        member.updateRefreshToken(newRefreshToken);

        return TokenResponseDTO.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();
    }

    @Transactional
    public void signOut(Long memberId) {
        Member member = getMemberById(memberId);
        member.updateRefreshToken(null);
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

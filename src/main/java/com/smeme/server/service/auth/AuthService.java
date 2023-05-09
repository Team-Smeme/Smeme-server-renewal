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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.smeme.server.util.message.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;

    private final MemberRepository memberRepository;

    private final AppleSignIn appleSignIn;

    private final KakaoSignIn kakaoSignIn;

    @Transactional
    public SignInResponseDTO signIn(String socialAccessToken, SignInRequestDTO signInRequestDTO) {
        String socialType = signInRequestDTO.socialType().toString();
        String socialId = login(signInRequestDTO.socialType(), socialAccessToken);

        boolean isRegistered = memberRepository.findBySocialId(socialId).isEmpty() ? false : true;

        if (isRegistered) {
            Member member = Member.builder()
                    .social(SocialType.valueOf(socialType))
                    .socialId(socialId)
                    .targetLang(LangType.en)
                    .build();

            memberRepository.save(member);
        }

        Long memberId = getMemberBySocialId(socialId).getId();

        Authentication authentication = new UserAuthentication(memberId, null, null);

        String refreshToken = jwtTokenProvider.generateRefreshToken(authentication);
        String accessToken = jwtTokenProvider.generateAccessToken(authentication);

        return SignInResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .isRegistered(isRegistered)
                .build();
    }

    @Transactional
    public TokenResponseDTO issueToken(Long memberId) {

        Authentication authentication = new UserAuthentication(memberId, null, null);

        String newAccessToken = jwtTokenProvider.generateAccessToken(authentication);
        String newRefreshToken = jwtTokenProvider.generateRefreshToken(authentication);

        Member member = getMember(memberId);
        member.updateRefreshToken(newRefreshToken);

        return TokenResponseDTO.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();
    }

    private Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException(INVALID_MEMBER.getMessage()));
    }

    private Member getMemberBySocialId(String socialId) {
        return memberRepository.findBySocialId(socialId)
                .orElseThrow(() -> new RuntimeException(INVALID_MEMBER.getMessage()));
    }

    private String login(SocialType socialType, String socialAccessToken) {
        return switch (socialType.toString()) {
            case "APPLE" -> appleSignIn.getAppleData(socialAccessToken);
            case "KAKAO" -> kakaoSignIn.getKakaoData(socialAccessToken);
            default -> throw new RuntimeException(INVALID_TOKEN.getMessage());
        };
    }
}

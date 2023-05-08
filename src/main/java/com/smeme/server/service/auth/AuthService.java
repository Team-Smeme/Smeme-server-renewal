package com.smeme.server.service.auth;

import com.smeme.server.config.jwt.JwtTokenProvider;
import com.smeme.server.config.jwt.UserAuthentication;
import com.smeme.server.dto.auth.SignInRequestDTO;
import com.smeme.server.dto.auth.SignInResponseDTO;
import com.smeme.server.model.LangType;
import com.smeme.server.model.Member;
import com.smeme.server.model.SocialType;
import com.smeme.server.repository.MemberRepository;
import com.smeme.server.util.message.ErrorMessage;
import com.smeme.server.util.message.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        String socialId;

        if (socialType.equals("APPLE")) {
            socialId = appleSignIn.getAppleData(socialAccessToken);
        } else if (socialType.equals("KAKAO")) {
            socialId = kakaoSignIn.getKakaoData(socialAccessToken);
        } else {
            throw new RuntimeException(ErrorMessage.INVALID_TOKEN.getMessage());
        }

        boolean isRegistered = true;

        if (memberRepository.findBySocialId(socialId).isEmpty()) {
            isRegistered = false;

            Member member = Member.builder()
                    .social(SocialType.valueOf(socialType))
                    .socialId(socialId)
                    .targetLang(LangType.en)
                    .build();

            memberRepository.save(member);
        }

        Long memberId = memberRepository.findBySocialId(socialId).get().getId();

        Authentication authentication = new UserAuthentication(memberId, null, null);

        String refreshToken = jwtTokenProvider.generateRefreshToken(authentication);
        String accessToken = jwtTokenProvider.generateAccessToken(authentication);

        return SignInResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .isRegistered(isRegistered)
                .build();
    }






}

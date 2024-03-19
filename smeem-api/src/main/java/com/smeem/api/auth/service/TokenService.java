package com.smeem.api.auth.service;


import com.smeem.api.auth.jwt.TokenProvider;
import com.smeem.api.auth.jwt.UserAuthentication;
import com.smeem.api.auth.jwt.SmeemToken;
import com.smeem.api.auth.service.dto.response.TokenServiceResponse;
import com.smeem.api.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TokenService {

    private static final Long ACCESS_TOKEN_EXPIRATION_TIME = 60 * 60 * 1000 * 2 * 12 * 1000000L; // 2시간
    private static final Long REFRESH_TOKEN_EXPIRATION_TIME = 60 * 60 * 1000 * 24 * 14L; // 2주

    private final MemberService memberService;
    private final TokenProvider tokenProvider;

    @Transactional
    public TokenServiceResponse issueToken(final long memberId) {
        val token = generateSmeemToken(UserAuthentication.create(memberId));
        val member = memberService.get(memberId);
        member.updateRefreshToken(token.getRefreshToken());
        return TokenServiceResponse.of(token);
    }

    public SmeemToken generateSmeemToken(Authentication authentication) {
        return SmeemToken.of(
                tokenProvider.generateToken(authentication, ACCESS_TOKEN_EXPIRATION_TIME),
                tokenProvider.generateToken(authentication, REFRESH_TOKEN_EXPIRATION_TIME));
    }
}

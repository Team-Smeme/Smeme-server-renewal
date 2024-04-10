package com.smeem.api.auth.service;

import com.smeem.api.auth.jwt.TokenProvider;
import com.smeem.api.auth.jwt.UserAuthentication;
import com.smeem.api.auth.jwt.SmeemToken;
import com.smeem.api.auth.service.dto.response.TokenServiceResponse;
import com.smeem.domain.member.adapter.member.MemberFinder;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.smeem.common.config.ValueConfig.ACCESS_TOKEN_EXPIRATION_TIME;
import static com.smeem.common.config.ValueConfig.REFRESH_TOKEN_EXPIRATION_TIME;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TokenService {

    private final MemberFinder memberFinder;

    private final TokenProvider tokenProvider;

    @Transactional
    public TokenServiceResponse issueToken(final long memberId) {
        val token = generateSmeemToken(UserAuthentication.create(memberId));
        val member = memberFinder.findById(memberId);
        member.updateRefreshToken(token.getRefreshToken());
        return TokenServiceResponse.of(token);
    }

    public SmeemToken generateSmeemToken(Authentication authentication) {
        return SmeemToken.of(
                tokenProvider.generateToken(authentication, ACCESS_TOKEN_EXPIRATION_TIME),
                tokenProvider.generateToken(authentication, REFRESH_TOKEN_EXPIRATION_TIME));
    }
}

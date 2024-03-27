package com.smeem.api.auth.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import lombok.val;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.smeem.api.auth.jwt.JwtValidationType.VALID_JWT;


@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenValidator tokenValidator;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws IOException, ServletException {
        final String token = getJwtFromRequest(request);
        if (isValidToken(token)) {
            val userId = tokenValidator.getUserFromJwt(token);
            UserAuthentication authentication = new UserAuthentication(userId, null, null);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private boolean isValidToken(String token) {
        return StringUtils.hasText(token) && tokenValidator.validateToken(token) == VALID_JWT;
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        return (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) ? bearerToken.substring("Bearer ".length()) : null;
    }
}
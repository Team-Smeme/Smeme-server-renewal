package com.smeem.http.web.filter;

import lombok.Builder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UserAuthentication extends UsernamePasswordAuthenticationToken {

    @Builder
    public UserAuthentication(
            Object principal,
            Object credentials,
            Collection<? extends GrantedAuthority> authorities
    ) {
        super(principal, credentials, authorities);
    }

    public static UserAuthentication create(Object principal) {
        return UserAuthentication.builder()
                .principal(principal)
                .credentials(null)
                .authorities(null)
                .build();
    }
}

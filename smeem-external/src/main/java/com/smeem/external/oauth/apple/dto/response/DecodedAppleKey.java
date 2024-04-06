package com.smeem.external.oauth.apple.dto.response;

import lombok.Builder;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record DecodedAppleKey(
        String kid,
        String alg
) {

    public static DecodedAppleKey of(String kid, String alg) {
        return DecodedAppleKey.builder()
                .kid(kid)
                .alg(alg)
                .build();
    }
}

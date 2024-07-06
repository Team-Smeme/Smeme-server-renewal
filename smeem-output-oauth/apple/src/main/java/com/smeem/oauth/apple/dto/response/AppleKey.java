package com.smeem.oauth.apple.dto.response;

public record AppleKey(
        String kty,
        String kid,
        String use,
        String alg,
        String n,
        String e
) {
}

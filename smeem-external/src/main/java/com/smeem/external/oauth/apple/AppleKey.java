package com.smeem.external.oauth.apple;

public record AppleKey(
        String kty,
        String kid,
        String use,
        String alg,
        String n,
        String e
) {
}

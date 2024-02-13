package com.smeem.api.auth.jwt;

public enum JwtValidationType {
    VALID_JWT,
    INVALID_JWT_TOKEN,
    INVALID_JWT_SIGNATURE,
    EXPIRED_JWT_TOKEN,
    UNSUPPORTED_JWT_TOKEN,
    EMPTY_JWT
}

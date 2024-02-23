package com.smeem.api.auth.jwt;

public enum JwtValidationType {
    VALID_JWT,
    INVALID_JWT,
    INVALID_JWT_SIGNATURE,
    EXPIRED_JWT,
    UNSUPPORTED_JWT,
    EMPTY_JWT
}

package com.smeem.api.support;

import com.smeem.external.oauth.exception.TokenException;
import lombok.NonNull;
import java.security.Principal;

import static com.smeem.common.code.failure.AuthFailureCode.INVALID_TOKEN;
import static java.util.Objects.isNull;

public class PrincipalConverter {

    public static long getMemberId(@NonNull Principal principal) {
        if (isNull(principal.getName())) {
            throw new TokenException(INVALID_TOKEN);
        }
        return Long.parseLong(principal.getName());
    }
}

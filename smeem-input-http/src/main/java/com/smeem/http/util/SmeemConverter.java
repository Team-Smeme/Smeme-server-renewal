package com.smeem.http.util;

import com.smeem.common.exception.ExceptionCode;
import com.smeem.common.exception.SmeemException;
import org.springframework.stereotype.Component;

import java.security.Principal;

@Component
public class SmeemConverter {

    public long toMemberId(Principal principal) {
        try {
            return Long.parseLong(principal.getName());
        } catch (Exception exception) {
            throw new SmeemException(ExceptionCode.UNAUTHORIZED);
        }
    }
}

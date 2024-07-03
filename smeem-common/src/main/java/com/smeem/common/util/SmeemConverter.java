package com.smeem.common.util;

import com.smeem.common.exception.ExceptionCode;
import com.smeem.common.exception.SmeemException;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class SmeemConverter {

    public long toMemberId(Principal principal) {
        try {
            return Long.parseLong(principal.getName());
        } catch (Exception exception) {
            throw new SmeemException(ExceptionCode.UNAUTHORIZED);
        }
    }

    public static String toString(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}

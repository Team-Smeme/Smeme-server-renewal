package com.smeem.common.util;

import static com.smeem.common.code.failure.AuthFailureCode.EMPTY_ACCESS_TOKEN;
import static java.util.Objects.*;

import java.net.URI;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.smeem.common.exception.TokenException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class Util {

    public static Long getMemberId(Principal principal) {
        if (isNull(principal)) {
            throw new TokenException(EMPTY_ACCESS_TOKEN);
        }
        return Long.valueOf(principal.getName());
    }

    public static URI getURI(String path, long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path(path)
                .buildAndExpand(id)
                .toUri();
    }

    public static String dateToString(LocalDateTime date) {
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public static LocalDateTime stringToDate(String str) {
        return LocalDateTime.parse(str + " 00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public static LocalDateTime getStartOfDay(LocalDateTime dateTime) {
        return dateTime.toLocalDate().atStartOfDay();
    }
}

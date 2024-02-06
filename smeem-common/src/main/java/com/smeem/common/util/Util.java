package com.smeem.common.util;

import static com.smeem.common.code.ErrorMessage.EMPTY_ACCESS_TOKEN;
import static java.util.Objects.*;

import java.net.URI;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class Util {

    public static Long getMemberId(Principal principal) {
        if (isNull(principal)) {
            throw new SecurityException(EMPTY_ACCESS_TOKEN.getMessage());
        }
        return Long.valueOf(principal.getName());
    }

    public static URI getURI(Long diaryId) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{diaryId}")
                .buildAndExpand(diaryId)
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

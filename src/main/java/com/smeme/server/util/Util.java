package com.smeme.server.util;

import static com.smeme.server.util.message.ErrorMessage.*;
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

    public static String transferDateTimeToString(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public static URI getURI(Long diaryId) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{diaryId}")
                .buildAndExpand(diaryId)
                .toUri();
    }

    public static LocalDateTime getStartOfDay(LocalDateTime dateTime) {
        return dateTime.toLocalDate().atStartOfDay();
    }
}

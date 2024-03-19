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

    public static long getMemberId(Principal principal) {
        if (isNull(principal)) {
            throw new TokenException(EMPTY_ACCESS_TOKEN);
        }
        return Long.parseLong(principal.getName());
    }

    public static URI getURI(String path, long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path(path)
                .buildAndExpand(id)
                .toUri();
    }

    public static String transferToLocalDateTime(LocalDateTime date) {
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}

package com.smeem.api.support;

import static com.smeem.common.code.failure.AuthFailureCode.EMPTY_ACCESS_TOKEN;
import static java.util.Objects.*;

import java.net.URI;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.smeem.external.oauth.exception.TokenException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class Util {

    //TODO: TimeConverter
    public static String transferToLocalDateTime(LocalDateTime date) {
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}

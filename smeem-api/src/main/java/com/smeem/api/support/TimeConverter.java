package com.smeem.api.support;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeConverter {

    public static String transferToLocalDateTime(LocalDateTime date) {
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}

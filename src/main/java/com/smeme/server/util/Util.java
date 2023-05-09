package com.smeme.server.util;

import static com.smeme.server.util.message.ErrorMessage.*;
import static java.util.Objects.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
}

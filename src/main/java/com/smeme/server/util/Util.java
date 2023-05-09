package com.smeme.server.util;

import static com.smeme.server.util.message.ErrorMessage.*;
import static java.util.Objects.*;

import java.security.Principal;

public class Util {

	public static Long getMemberId(Principal principal) {
		if (isNull(principal)) {
			throw new SecurityException(EMPTY_ACCESS_TOKEN.getMessage());
		}
		return Long.valueOf(principal.getName());
	}
}

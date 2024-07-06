package com.smeem.oauth.apple.dto.response;

import java.util.List;

public record AppleKeysResponse(
        List<AppleKey> keys
) {
}

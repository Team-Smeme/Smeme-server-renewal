package com.smeem.external.oauth.apple.dto.response;

import java.util.List;

public record AppleKeyListResponse(
        List<AppleKey> keys
) {
}

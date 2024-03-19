package com.smeem.external.oauth.apple;

import java.util.List;

public record AppleKeyListResponse(
        List<AppleKey> keys
) {
}

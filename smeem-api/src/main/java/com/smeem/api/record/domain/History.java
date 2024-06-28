package com.smeem.api.record.domain;

import jakarta.validation.constraints.NotNull;

public record History(
        @NotNull String reasonForWithdraw
) {
}

package com.smeem.application.domain.withdraw;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Withdraw {
    public Long id;
    @NotNull
    private WithdrawType withdrawType;
    private String reason;
}

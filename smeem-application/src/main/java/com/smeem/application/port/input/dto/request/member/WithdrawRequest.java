package com.smeem.application.port.input.dto.request.member;

import com.smeem.application.domain.withdraw.Withdraw;
import com.smeem.application.domain.withdraw.WithdrawType;

public record WithdrawRequest(
        WithdrawType withdrawType,
        String reason
) {

    public Withdraw toDomain() {
        return Withdraw.builder()
                .withdrawType(withdrawType)
                .reason(reason)
                .build();
    }
}

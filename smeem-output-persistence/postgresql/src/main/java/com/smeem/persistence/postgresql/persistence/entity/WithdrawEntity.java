package com.smeem.persistence.postgresql.persistence.entity;

import com.smeem.application.domain.withdraw.Withdraw;
import com.smeem.application.domain.withdraw.WithdrawType;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "withdraw", schema = "smeem")
@NoArgsConstructor
public class WithdrawEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WithdrawType withdrawType;
    private String reason;

    public WithdrawEntity(Withdraw withdraw) {
        withdrawType = withdraw.getWithdrawType();
        reason = withdraw.getReason();
    }
}

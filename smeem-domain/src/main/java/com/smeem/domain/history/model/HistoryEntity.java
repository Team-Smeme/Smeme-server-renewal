package com.smeem.domain.history.model;

import com.smeem.api.record.domain.History;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "history")
@NoArgsConstructor
public class HistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reasonForWithdraw;

    public HistoryEntity(History history) {
        this.reasonForWithdraw = history.reasonForWithdraw();
    }
}

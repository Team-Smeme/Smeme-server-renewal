package com.smeem.persistence.postgresql.persistence.entity;

import com.smeem.application.domain.diary.Correction;
import com.smeem.application.domain.diary.Diary;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "correction", schema = "smeem")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CorrectionEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String originContent;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String correctedContent;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String reason;

    @Column(nullable = false)
    private long memberId;

    @Column(nullable = false)
    private long diaryId;

    @Column(nullable = false)
    private UUID key;

    public CorrectionEntity(Correction correction, Diary diary, UUID key) {
        this.originContent = correction.originalSentence();
        this.correctedContent = correction.correctedSentence();
        this.reason = correction.reason();
        this.memberId = diary.getMemberId();
        this.diaryId = diary.getId();
        this.key = key;
    }

    public Correction toDomain() {
        return Correction.builder()
                .originalSentence(originContent)
                .correctedSentence(correctedContent)
                .reason(reason)
                .build();
    }
}

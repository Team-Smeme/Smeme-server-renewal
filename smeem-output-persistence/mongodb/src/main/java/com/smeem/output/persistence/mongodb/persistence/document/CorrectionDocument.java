package com.smeem.output.persistence.mongodb.persistence.document;

import com.smeem.application.domain.diary.Correction;
import com.smeem.application.domain.diary.Diary;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collation = "smeem")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CorrectionDocument {
    @Id
    private String id;
    private List<CorrectionDetail> corrections;
    private long memberId;
    private long diaryId;
    @CreatedDate
    private LocalDateTime createdAt;

    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    private static class CorrectionDetail {
        private String originContent;
        private String correctedContent;
        private String reason;
        private boolean corrected;

        public CorrectionDetail(Correction correction) {
            this.originContent = correction.originalSentence();
            this.correctedContent = correction.correctedSentence();
            this.corrected = correction.isCorrected();
            this.reason = corrected ? correction.reason() : null;
        }

        public Correction toDomain() {
            return Correction.builder()
                    .originalSentence(originContent)
                    .correctedSentence(correctedContent)
                    .reason(reason)
                    .isCorrected(corrected)
                    .build();
        }
    }

    public CorrectionDocument(List<Correction> corrections, Diary diary) {
        this.corrections = corrections.stream().map(CorrectionDetail::new).toList();
        this.memberId = diary.getMemberId();
        this.diaryId = diary.getId();
    }

    public List<Correction> toDomain() {
        return this.corrections.stream().map(CorrectionDetail::toDomain).toList();
    }
}

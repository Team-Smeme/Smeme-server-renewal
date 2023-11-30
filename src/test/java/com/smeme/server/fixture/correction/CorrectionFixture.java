package com.smeme.server.fixture.correction;

import com.smeme.server.dto.correction.CorrectionRequestDTO;
import com.smeme.server.dto.correction.CorrectionResponseDTO;
import com.smeme.server.fixture.badge.BadgeFixture;
import com.smeme.server.fixture.diary.DiaryFixture;
import com.smeme.server.fixture.member.MemberFixture;
import com.smeme.server.model.Correction;

public class CorrectionFixture  {

    private static final String CORRECTION_SENTENCE = "correction test sentence";
    private static final String CORRECTION_CONTENT = "correction test content";
    private static final String BEFORE_SENTENCE ="correction before sentence";
    private static final String AFTER_SENTENCE ="correction after sentence";

    private static final Long DIARY_ID = 1L;

    public static Correction createCorrection() {
        return Correction.builder()
                .beforeSentence(BEFORE_SENTENCE)
                .afterSentence(AFTER_SENTENCE)
                .diary(DiaryFixture.createDiary())
                .build();
    }

    public static CorrectionResponseDTO createCorrectionResponseDTO() {
        return CorrectionResponseDTO.of(
                DIARY_ID,
                BadgeFixture.createBadge()
        );
    }

    public static CorrectionRequestDTO createCorrectionRequestDTO() {
        return new CorrectionRequestDTO(
                CORRECTION_SENTENCE,
                CORRECTION_CONTENT
        );
    }
}

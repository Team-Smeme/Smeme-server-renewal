package com.smeem.output.persistence.mongodb.persistence.repository;

import com.smeem.output.persistence.mongodb.persistence.document.CorrectionDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface CorrectionRepository extends MongoRepository<CorrectionDocument, String> {
    int countByMemberIdAndCreatedAtBetween(long memberId, LocalDateTime startAt, LocalDateTime endAt);

    Optional<CorrectionDocument> findByDiaryId(long diaryId);

    void deleteByDiaryId(long diaryId);

    void deleteByMemberId(long memberId);

    int countByMemberId(long memberId);
}

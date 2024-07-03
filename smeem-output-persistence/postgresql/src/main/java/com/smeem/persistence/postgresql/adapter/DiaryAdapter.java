package com.smeem.persistence.postgresql.adapter;

import com.smeem.application.domain.diary.Diary;
import com.smeem.application.port.output.persistence.DiaryPort;
import com.smeem.common.exception.ExceptionCode;
import com.smeem.common.exception.SmeemException;
import com.smeem.persistence.postgresql.persistence.entity.DiaryEntity;
import com.smeem.persistence.postgresql.persistence.entity.MemberEntity;
import com.smeem.persistence.postgresql.persistence.entity.TopicEntity;
import com.smeem.persistence.postgresql.persistence.repository.DiaryRepository;
import com.smeem.persistence.postgresql.persistence.repository.MemberRepository;
import com.smeem.persistence.postgresql.persistence.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class DiaryAdapter implements DiaryPort {
    private final DiaryRepository diaryRepository;
    private final MemberRepository memberRepository;
    private final TopicRepository topicRepository;

    @Override
    public int countByMember(long memberId) {
        return diaryRepository.countByMemberId(memberId);
    }

    @Override
    public Diary save(Diary diary) {
        return diaryRepository.save(DiaryEntity.of(diary)).toDomain();
    }

    @Override
    public boolean isExistByMemberAndYesterday(long memberId) {
        return diaryRepository.existsByMemberIdAndYesterday(memberId);
    }

    @Override
    public Diary findByIdJoinMemberAndTopic(long id) {
        val foundDiary = find(id);
        val member = findByMemberId(foundDiary.getMemberId()).toDomain();
        val topic = foundDiary.getTopicId() != null ? findByTopicId(foundDiary.getTopicId()).toDomain() : null;
        return foundDiary.toDomain(member, topic);
    }

    @Override
    public Diary findById(long id) {
        return find(id).toDomain();
    }

    @Override
    public Diary update(Diary diary) {
        val foundDiary = find(diary.getId());
        return foundDiary.update(diary).toDomain();
    }

    @Override
    public void deleteById(long id) {
        diaryRepository.deleteById(id);
    }

    @Override
    public List<Diary> findByMemberAndTerm(long memberId, LocalDate startAt, LocalDate endAt) {
        return diaryRepository.findByMemberIdBetween(memberId, startAt, endAt).stream()
                .map(DiaryEntity::toDomain)
                .toList();
    }

    @Override
    public boolean isExistByMemberAndPastAgo(long memberId, int days) {
        return diaryRepository.existsByMemberAndPastAgo(memberId, days);
    }

    private DiaryEntity find(long id) {
        return diaryRepository.findById(id)
                .orElseThrow(() -> new SmeemException(ExceptionCode.NOT_FOUND, " id: " + id));
    }

    private MemberEntity findByMemberId(long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new SmeemException(ExceptionCode.NOT_FOUND, " id: " + id));
    }

    private TopicEntity findByTopicId(long id) {
        return topicRepository.findById(id)
                .orElseThrow(() -> new SmeemException(ExceptionCode.NOT_FOUND, " id: " + id));
    }
}

package com.smeem.api.diary.service;


import com.smeem.api.badge.service.BadgeService;
import com.smeem.api.diary.controller.dto.request.DiaryRequestDTO;
import com.smeem.api.diary.controller.dto.response.CreatedDiaryResponseDTO;
import com.smeem.api.diary.controller.dto.response.DiariesResponseDTO;
import com.smeem.api.diary.controller.dto.response.DiaryResponseDTO;
import com.smeem.api.member.service.MemberService;
import com.smeem.api.topic.service.TopicService;
import com.smeem.common.config.ValueConfig;
import com.smeem.common.exception.DiaryException;
import com.smeem.common.util.Util;
import com.smeem.domain.badge.model.Badge;
import com.smeem.domain.diary.model.DeletedDiary;
import com.smeem.domain.diary.model.Diary;
import com.smeem.domain.diary.repository.DeletedDiaryRepository;
import com.smeem.domain.diary.repository.DiaryRepository;
import com.smeem.domain.member.model.Member;
import com.smeem.domain.topic.model.Topic;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.smeem.common.code.failure.DiaryFailureCode.EXIST_TODAY_DIARY;
import static com.smeem.common.code.failure.DiaryFailureCode.INVALID_DIARY;
import static java.lang.Integer.parseInt;
import static java.time.LocalDateTime.now;
import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final DeletedDiaryRepository deletedDiaryRepository;

    private final BadgeService badgeService;
    private final TopicService topicService;
    private final MemberService memberService;

    private final ValueConfig valueConfig;

    @Transactional
    public CreatedDiaryResponseDTO save(Long memberId, DiaryRequestDTO request) {
        Member member = memberService.get(memberId);

        if (member.wroteDiaryToday()) {
            throw new DiaryException(EXIST_TODAY_DIARY);
        }

        Topic topic = topicService.get(request.topicId());
        Diary diary = diaryRepository.save(new Diary(request.content(), topic, member));

        List<Badge> badges = obtainBadges(member, diary.getCreatedAt());
        badges.forEach(badge -> badgeService.saveMemberBadge(member, badge));

        return CreatedDiaryResponseDTO.of(diary.getId(), badges);
    }

    public DiaryResponseDTO getDetail(Long id) {
        Diary diary = get(id);
        return DiaryResponseDTO.of(diary);
    }

    @Transactional
    public void update(Long id, DiaryRequestDTO request) {
        Diary diary = get(id);
        diary.updateContent(request.content());
    }

    @Transactional
    public void delete(Long id) {
        Diary diary = get(id);
        copyToDeletedDiary(diary);
        delete(diary);
    }

    private void copyToDeletedDiary(Diary diary) {
        deletedDiaryRepository.save(new DeletedDiary(diary));
    }

    private void delete(Diary diary) {
        diary.deleteFromMember();
        diaryRepository.deleteById(diary.getId());
    }

    public DiariesResponseDTO getDiaries(Long memberId, String startDate, String endDate) {
        Member member = memberService.get(memberId);
        List<Diary> diaries = member.getDiaries().stream()
                .filter(diary -> diary.isBetween(Util.stringToDate(startDate), Util.stringToDate(endDate)))
                .toList();
        boolean hasRemind = member.getDiaries().stream()
                .anyMatch(diary -> diary.isCreatedAt(now().minusDays(parseInt(valueConfig.getDURATION_REMIND()))));

        return DiariesResponseDTO.of(diaries, hasRemind);
    }

    @Transactional
    public void deleteExpiredDiary() {
        LocalDateTime expiryDate = getExpiryDate();
        deletedDiaryRepository.deleteByUpdatedAtBefore(expiryDate);
    }

    private LocalDateTime getExpiryDate() {
        int expiredDay = parseInt(valueConfig.getDURATION_EXPIRED()) - 1;
        return LocalDate.now().minusDays(expiredDay).atStartOfDay();
    }

    @Transactional
    public void deleteAllByMember(Member member) {
        diaryRepository.deleteAllByMember(member);
        deletedDiaryRepository.deleteByMember(member);
    }

    public List<Diary> getAllByMemberId(Long memberId) {
        return diaryRepository.findAllByMemberId(memberId);
    }

    public Diary get(Long id) {
        return diaryRepository.findById(id)
                .orElseThrow(() -> new DiaryException(INVALID_DIARY));
    }

    private List<Badge> obtainBadges(Member member, LocalDateTime createdAt) {
        List<Badge> badges = new ArrayList<>();

        Badge countingBadge = obtainCountingBadge(member);
        if (nonNull(countingBadge) && member.hasNotBadge(countingBadge)) {
            badges.add(countingBadge);
        }

        Badge comboBadge = obtainComboBadge(member, createdAt);
        if (nonNull(comboBadge) && member.hasNotBadge(comboBadge)) {
            badges.add(comboBadge);
        }

        return badges;
    }

    private Badge obtainCountingBadge(Member member) {
        int count = member.getDiaries().stream().toList().size();

        return switch (count) {
            case 50 -> badgeService.get(5L);
            case 30 -> badgeService.get(4L);
            case 10 -> badgeService.get(3L);
            case 1 -> badgeService.get(2L);
            default -> null;
        };
    }

    private Badge obtainComboBadge(Member member, LocalDateTime createdAt) {
        boolean isCombo = member.getDiaries().stream()
                .anyMatch(diary -> diary.isCreatedAt(createdAt.minusDays(1)));

        member.updateDiaryCombo(isCombo);

        return switch (member.getDiaryComboCount()) {
            case 30 -> badgeService.get(9L);
            case 15 -> badgeService.get(8L);
            case 7 -> badgeService.get(7L);
            case 3 -> badgeService.get(6L);
            default -> null;
        };
    }
}

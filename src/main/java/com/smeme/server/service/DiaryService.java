package com.smeme.server.service;

import static com.smeme.server.util.Util.stringToDate;
import static com.smeme.server.util.message.ErrorMessage.*;
import static java.lang.Integer.parseInt;
import static java.time.LocalDateTime.now;
import static java.util.Objects.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import com.smeme.server.config.ValueConfig;
import com.smeme.server.model.DeletedDiary;
import com.smeme.server.repository.correction.CorrectionRepository;
import com.smeme.server.repository.diary.DeletedDiaryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smeme.server.dto.diary.CreatedDiaryResponseDTO;
import com.smeme.server.dto.diary.DiariesResponseDTO;
import com.smeme.server.dto.diary.DiaryRequestDTO;
import com.smeme.server.dto.diary.DiaryResponseDTO;
import com.smeme.server.model.Diary;
import com.smeme.server.model.Member;
import com.smeme.server.model.badge.Badge;
import com.smeme.server.model.topic.Topic;
import com.smeme.server.repository.diary.DiaryRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final CorrectionRepository correctionRepository;
    private final DeletedDiaryRepository deletedDiaryRepository;

    private final BadgeService badgeService;
    private final TopicService topicService;
    private final MemberService memberService;

    private final ValueConfig valueConfig;

    @Transactional
    public CreatedDiaryResponseDTO save(Long memberId, DiaryRequestDTO request) {
        Member member = memberService.get(memberId);

        if (member.wroteDiaryToday()) {
            throw new IllegalStateException(EXIST_TODAY_DIARY.getMessage());
        }

        Topic topic = topicService.get(request.topicId());
        Diary diary = diaryRepository.save(new Diary(request.content(), topic, member));

        List<Badge> badges = obtainBadges(member, diary.getCreatedAt());
        badges.forEach(badge -> badgeService.saveMemberBadge(member, badge));

        return CreatedDiaryResponseDTO.of(diary.getId(), badges);
    }

    public DiaryResponseDTO getDetail(Long id) {
        Diary diary = getFetchJoinCorrections(id);
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
        correctionRepository.deleteAll(diary.getCorrections());
        diaryRepository.deleteById(diary.getId());
    }

    public DiariesResponseDTO getDiaries(Long memberId, String startDate, String endDate) {
        Member member = memberService.get(memberId);
        List<Diary> diaries = member.getDiaries().stream()
                .filter(diary -> diary.isBetween(stringToDate(startDate), stringToDate(endDate)))
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
    }

    public List<Diary> getAllByMemberId(Long memberId) {
        return diaryRepository.findAllByMemberId(memberId);
    }

    protected Diary get(Long id) {
        return diaryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(INVALID_DIARY.getMessage()));
    }

    protected Diary getFetchJoinCorrections(Long id) {
        return diaryRepository.findByIdFetchJoinCorrections(id)
                .orElseThrow(() -> new EntityNotFoundException(INVALID_DIARY.getMessage()));
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

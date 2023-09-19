package com.smeme.server.service;

import static com.smeme.server.util.Util.getStartOfDay;
import static com.smeme.server.util.message.ErrorMessage.*;
import static java.util.Objects.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import com.smeme.server.model.badge.MemberBadge;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smeme.server.dto.diary.CreatedDiaryResponseDTO;
import com.smeme.server.dto.diary.DiariesResponseDTO;
import com.smeme.server.dto.diary.DiaryRequestDTO;
import com.smeme.server.dto.diary.DiaryResponseDTO;
import com.smeme.server.model.Correction;
import com.smeme.server.model.Diary;
import com.smeme.server.model.Member;
import com.smeme.server.model.badge.Badge;
import com.smeme.server.model.topic.Topic;
import com.smeme.server.repository.badge.BadgeRepository;
import com.smeme.server.repository.correction.CorrectionRepository;
import com.smeme.server.repository.diary.DiaryRepository;
import com.smeme.server.repository.MemberRepository;
import com.smeme.server.repository.topic.TopicRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DiaryService {

    private final DiaryRepository diaryRepository;
//    private final TopicRepository topicRepository;
    private final MemberRepository memberRepository;
    private final CorrectionRepository correctionRepository;
    private final BadgeRepository badgeRepository;

    private final BadgeService badgeService;
    private final TopicService topicService;

    @Transactional
    public CreatedDiaryResponseDTO createDiary(Long memberId, DiaryRequestDTO request) {

        /**
         * TODO: 논의 후 리팩토링
         * 1. Member.diaryComboCount 칼럼 추가
         * 2. getTopic, getBadge 등 각 Service 메서드 이동
         * 3. diaryComboCount 칼럼 추가하면 임시 API 만들어서 회원 diaryComboCount 현재까지 계산하기
         * 3-1. API1. 전날까지 계산 now-1까지 combo 계산
         * 3-2. API2. 금일까지 계산 now 일기 작성했으면 combo+1 (이건 수작업으로 해도 될 듯 ..?)
         */

        Member member = getMember(memberId);

        if (member.isExistTodayDiary()) {
            throw new IllegalStateException(EXIST_TODAY_DIARY.getMessage());
        }

        //TODO: service - repository 배치 논의 필요
        Topic topic = topicService.getTopic(request.topicId()); // getTopic(request.topicId());
        Diary diary = diaryRepository.save(new Diary(request.content(), topic, member));

        List<Badge> badges = getDiaryBadge(member, diary.getCreatedAt());
        badges.forEach(badge -> badgeService.createMemberBadge(member, badge));

        return CreatedDiaryResponseDTO.of(diary.getId(), badges);
    }

    public DiaryResponseDTO getDiaryDetail(Long diaryId) {
        /**
         * TODO
         * 1. correction fetch join
         */

        Diary diary = getDiary(diaryId); // correction fetch join
        List<Correction> corrections = correctionRepository.findByDiaryOrderByIdDesc(diary);
        return DiaryResponseDTO.of(diary, corrections);
    }

    @Transactional
    public void updateDiary(Long diaryId, DiaryRequestDTO request) {
        Diary diary = getDiary(diaryId); // fetch X
        diary.updateContent(request.content());
    }

    @Transactional
    public void deleteDiary(Long diaryId) {
        /**
         * TODO
         * 1. (확인 필요) 자식 객체인 correction이 모두 삭제되는가? (고아 객체 추가, update 실행)
         * 2. (확인 필요) 부모 객체인 member.getDiaries()에서 삭제한 diary 객체가 빠지는가?
         */

        Diary diary = getDiary(diaryId);
        diary.delete();
    }

    public DiariesResponseDTO getDiaries(Long memberId, String startDate, String endDate) {

        /**
         * TODO: API 테스트 필요
         * - 단순 쿼리에서 stream으로 수정
         * - 쿼리 몇 개 날아가는지 확인 필요
         *
         * 1. 30 : expired.duration @Value 값으로 수정하기
         * 2. transferStringToDateTime Util로 이동, 이름 간략하게 수정
         */

        Member member = getMember(memberId);

        LocalDateTime startAt = transferStringToDateTime(startDate);
        LocalDateTime endAt = transferStringToDateTime(endDate);
        List<Diary> diaries = member.getDiaries().stream()
                .filter(diary -> isBetween(diary.getCreatedAt(), startAt, endAt))
                .toList();

        LocalDateTime now = getStartOfDay(LocalDateTime.now());
        boolean has30Past = member.getDiaries().stream()
                .anyMatch(diary -> getStartOfDay(diary.getCreatedAt()).plusDays(30).equals(now));

        return DiariesResponseDTO.of(diaries, has30Past);
    }

    private boolean isBetween(LocalDateTime target, LocalDateTime startAt, LocalDateTime endAt) {
        return target.isEqual(startAt) || (target.isAfter(startAt) && target.isBefore(endAt)) || target.isEqual(endAt);
    }

    @Transactional
    public void deleteDiary30Past(LocalDateTime past) {
        /**
         * TODO
         * 1. 가능하면 벌크 연산으로 수정
         * 2. 자식 객체 correction 삭제되는가? (위에서 확인 가능)
         * 3. 부모 객체 member.getDiaries()에서 삭제한 diary가 빠지는가? (위에서 확인 가능)
         */

//        // diary만 삭제하면 member.getDiaries() 어떻게 될까? 관계 끊어지는 거 확인되면 벌크업 연산
//        Member me = getMember(827L);
//        diaryRepository.deleteByMemberAndExpired(me);
//        // 확인
//        System.out.println(me.getDiaries());
//        // correction 확인

        diaryRepository.findDiariesDeleted30Past(past)
                .forEach(this::delete);
    }

    private void delete(Diary diary) {
        diary.deleteFromMember();
        correctionRepository.deleteAll(diary.getCorrections());
        diaryRepository.deleteById(diary.getId());
    }

    private Diary getDiary(Long diaryId) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new EntityNotFoundException(INVALID_DIARY.getMessage()));
        if (diary.isDeleted()) {
            throw new NoSuchElementException(DELETED_DIARY.getMessage());
        }
        return diary;
    }

    private Member getMember(Long memberId) {
        /**
         * TODO: 논의해보고 memberService로 이동
         */
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(INVALID_MEMBER.getMessage()));
    }

//    private Topic getTopic(Long topicId) {
//        return nonNull(topicId)
//                ? topicRepository.findById(topicId)
//                .orElseThrow(() -> new EntityNotFoundException(INVALID_TOPIC.getMessage()))
//                : null;
//    }

    private LocalDateTime transferStringToDateTime(String str) {
        /**
         * TODO: util로, 메서드명 좀 더 간단하게
         */
        return LocalDateTime.parse(str + " 00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    private List<Badge> getDiaryBadge(Member member, LocalDateTime createdDate) {
        List<Badge> badges = new ArrayList<>();

        Badge countingBadge = getCountingDiaryBadge(member); // 누적 개수
        if (nonNull(countingBadge) && isNotExistMemberBadge(member.getBadges(), countingBadge)) {
            badges.add(countingBadge);
        }

        Badge comboBadge = getComboDiaryBadge(member, createdDate); // 콤보 개수
        if (nonNull(comboBadge) && isNotExistMemberBadge(member.getBadges(), comboBadge)) {
            badges.add(comboBadge);
        }

        return badges;
    }

    private boolean isNotExistMemberBadge(List<MemberBadge> memberBadges, Badge badge) {
        return memberBadges.stream().noneMatch(memberBadge -> memberBadge.getBadge().equals(badge));
    }

    private Badge getComboDiaryBadge(Member member, LocalDateTime createdDate) {

        //TODO: 리팩토링 : diaryComboCount 칼럼 필요
        /*
        LocalDateTime createdAt = createdDate;
        boolean isCombo = member.getDiaries().stream()
                .anyMatch(diary -> getStartOfDay(diary.getCreatedAt()).plusDays(1).equals(getStartOfDay(createdAt)));
        member.updateDiaryCombo(isCombo);
        return switch (member.getDiaryComboCount()) {
            case 30 -> getBadge(9L);
            case 15 -> getBadge(8L);
            case 7 -> getBadge(7L);
            case 3 -> getBadge(6L);
            default -> null;
        };*/

        int comboCount = 0;
        while (diaryRepository.existDiaryInDate(member, createdDate)) {
            comboCount++;
            createdDate = createdDate.minusDays(1);
        }

        return switch (comboCount) {
            case 30 -> getBadge(9L);
            case 15 -> getBadge(8L);
            case 7 -> getBadge(7L);
            case 3 -> getBadge(6L);
            default -> null;
        };
    }

    private Badge getCountingDiaryBadge(Member member) {
        /**
         * TODO
         * 1. diaryCount 제대로 나오는 지 확인
         * 2. 일기 delete 하고 diaries() remove 업데이트 해서 괜찮을걸 ?
         */

        int diaryCount = member.getDiaries().size();
//        int diaryCount = member.getDiaries().stream().filter(diary -> !diary.isDeleted()).toList().size();


        return switch (diaryCount) {
            case 50 -> getBadge(5L);
            case 30 -> getBadge(4L);
            case 10 -> getBadge(3L);
            case 1 -> getBadge(2L);
            default -> null;
        };
    }

    private Badge getBadge(Long id) { // BadgeService
        return badgeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(INVALID_BADGE.getMessage()));
    }
}

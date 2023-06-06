package com.smeme.server.service;

import static com.smeme.server.util.message.ErrorMessage.*;
import static java.util.Objects.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

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
import com.smeme.server.repository.BadgeRepository;
import com.smeme.server.repository.correction.CorrectionRepository;
import com.smeme.server.repository.MemberBadgeRepository;
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
	private final TopicRepository topicRepository;
	private final MemberRepository memberRepository;
	private final CorrectionRepository correctionRepository;
	private final MemberBadgeRepository memberBadgeRepository;
	private final BadgeRepository badgeRepository;
	private final BadgeService badgeService;

	@Transactional
	public CreatedDiaryResponseDTO createDiary(Long memberId, DiaryRequestDTO requestDTO) {
		Member member = getMember(memberId);
		Topic topic = getTopic(requestDTO.topicId());
		Diary diary = checkExistTodayDiary(member, new Diary(requestDTO.content(), topic, member));
		List<Badge> badges = getDiaryBadge(member, diary.getCreatedAt());
		return CreatedDiaryResponseDTO.of(diary.getId(), badges);
	}

	public DiaryResponseDTO getDiaryDetail(Long diaryId) {
		Diary diary = getDiary(diaryId);
		List<Correction> corrections = correctionRepository.findByDiaryOrderByIdDesc(diary);
		return DiaryResponseDTO.of(diary, corrections);
	}

	@Transactional
	public void updateDiary(Long diaryId, DiaryRequestDTO requestDTO) {
		Diary diary = getDiary(diaryId);
		diary.updateContent(requestDTO.content());
	}

	@Transactional
	public void deleteDiary(Long diaryId) {
		Diary diary = getDiary(diaryId);
		diary.deleteDiary();
	}

	public DiariesResponseDTO getDiaries(Long memberId, String startDate, String endDate) {
		Member member = getMember(memberId);
		List<Diary> diaries = diaryRepository.findDiariesStartToEnd(member,
			transferStringToDateTime(startDate),
			transferStringToDateTime(endDate).plusDays(1));
		boolean has30Past = diaryRepository.exist30PastDiary(member);
		return DiariesResponseDTO.of(diaries, has30Past);
	}

	@Transactional
	public void deleteDiary30Past(LocalDateTime past) {
		diaryRepository.findDiariesDeleted30Past(past)
			.forEach(this::deleteDiaryRelation);
	}

	private void deleteDiaryRelation(Diary diary) {
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
		return memberRepository.findById(memberId)
			.orElseThrow(() -> new EntityNotFoundException(INVALID_MEMBER.getMessage()));
	}

	private Topic getTopic(Long topicId) {
		return nonNull(topicId)
			? topicRepository.findById(topicId)
			.orElseThrow(() -> new EntityNotFoundException(INVALID_TOPIC.getMessage()))
			: null;
	}

	private Diary checkExistTodayDiary(Member member, Diary diary) {
		if (diaryRepository.existTodayDiary(member)) {
			throw new IllegalArgumentException(EXIST_TODAY_DIARY.getMessage());
		}
		return diaryRepository.save(diary);
	}

	private LocalDateTime transferStringToDateTime(String str) {
		return LocalDateTime.parse(str + " 00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
	}


	private List<Badge> getDiaryBadge(Member member, LocalDateTime createdDate) {
		List<Badge> badges = new ArrayList<>();
		Badge countingBadge = getCountingDiaryBadge(member);
		if (Objects.nonNull(countingBadge) && !memberBadgeRepository.existsByMemberAndBadge(member, countingBadge)) {
			badges.add(countingBadge);
		}
		Badge comboBadge = getComboDiaryBadge(member, createdDate);
		if (Objects.nonNull(comboBadge) && !memberBadgeRepository.existsByMemberAndBadge(member, comboBadge)) {
			badges.add(comboBadge);
		}
		badges.forEach(badge -> badgeService.createMemberBadge(member, badge));
		return badges;
	}

	private Badge getComboDiaryBadge(Member member, LocalDateTime createdDate) {
		int comboCount = 0;
		while (diaryRepository.existDiaryInDate(member, createdDate)) {
			comboCount++;
			createdDate = createdDate.minusDays(1);
		}
		if (comboCount == 30) {
			return getBadge(9L);
		} else if (comboCount == 15) {
			return getBadge(8L);
		} else if (comboCount == 7) {
			return getBadge(7L);
		} else if (comboCount == 3) {
			return getBadge(6L);
		}
		return null;
	}

	private Badge getCountingDiaryBadge(Member member) {
		int diaryCount = member.getDiaries().stream().filter(diary -> !diary.isDeleted()).toList().size();
		if (diaryCount == 50) {
			return getBadge(5L);
		} else if (diaryCount == 30) {
			return getBadge(4L);
		} else if (diaryCount == 10) {
			return getBadge(3L);
		} else if (diaryCount == 1) {
			return getBadge(2L);
		}
		return null;
	}

	private Badge getBadge(Long id) {
		return badgeRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException(INVALID_BADGE.getMessage()));
	}
}

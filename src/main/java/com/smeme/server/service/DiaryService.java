package com.smeme.server.service;

import static com.smeme.server.util.message.ErrorMessage.*;
import static java.util.Objects.*;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import com.smeme.server.dto.diary.DiaryRequestDTO;
import com.smeme.server.dto.diary.DiaryResponseDTO;
import com.smeme.server.model.Correction;
import com.smeme.server.model.Diary;
import com.smeme.server.model.Member;
import com.smeme.server.model.topic.Topic;
import com.smeme.server.repository.CorrectionRepository;
import com.smeme.server.repository.diary.DiaryRepository;
import com.smeme.server.repository.MemberRepository;
import com.smeme.server.repository.TopicRepository;

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

	@Transactional
	public Long createDiary(Long memberId, DiaryRequestDTO requestDTO) {
		Member member = getMember(memberId);
		Topic topic = getTopic(requestDTO.topicId());
		Diary diary = new Diary(requestDTO.content(), topic, member);
		if (!existTodayDiary(member)) {
			diaryRepository.save(diary);
		}
		return diary.getId();
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

	private Diary getDiary(Long diaryId) {
		Diary diary = diaryRepository.findById(diaryId)
			.orElseThrow(() -> new EntityNotFoundException(INVALID_DIARY.getMessage()));
		if (diary.isDeleted()) {
			throw new NotFoundException(DELETED_DIARY.getMessage());
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

	private boolean existTodayDiary(Member member) {
		if (diaryRepository.existTodayDiary(member)) {
			throw new IllegalArgumentException(EXIST_TODAY_DIARY.getMessage());
		}
		return false;
	}

}

package com.smeme.server.service;

import static com.smeme.server.util.message.ErrorMessage.*;

import org.springframework.stereotype.Service;

import com.smeme.server.dto.diary.DiaryRequestDTO;
import com.smeme.server.model.Diary;
import com.smeme.server.model.Member;
import com.smeme.server.model.topic.Topic;
import com.smeme.server.repository.diary.DiaryRepository;
import com.smeme.server.repository.MemberRepository;
import com.smeme.server.repository.TopicRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DiaryService {

	private final DiaryRepository diaryRepository;
	private final TopicRepository topicRepository;
	private final MemberRepository memberRepository;

	public Long createDiary(Long memberId, DiaryRequestDTO requestDTO) {
		Member member = getMember(memberId);
		Topic topic = getTopic(requestDTO.topicId());
		Diary diary = new Diary(requestDTO.content(), topic, member);
		if (!existTodayDiary(member)) {
			diaryRepository.save(diary);
		}
		return diary.getId();
	}

	private Member getMember(Long memberId) {
		return memberRepository.findById(memberId)
			.orElseThrow(() -> new EntityNotFoundException(INVALID_MEMBER.getMessage()));
	}

	private Topic getTopic(Long topicId) {
		return topicRepository.findById(topicId)
			.orElseThrow(() -> new EntityNotFoundException(INVALID_TOPIC.getMessage()));
	}

	private boolean existTodayDiary(Member member) {
		if (diaryRepository.existTodayDiary(member)) {
			throw new IllegalArgumentException(EXIST_TODAY_DIARY.getMessage());
		}
		return false;
	}

}

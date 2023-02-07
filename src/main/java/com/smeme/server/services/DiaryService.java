package com.smeme.server.services;

import com.smeme.server.dtos.diary.DiaryCreateRequestDto;
import com.smeme.server.dtos.diary.DiaryCreateResponseDto;
import com.smeme.server.models.Diary;
import com.smeme.server.models.Topic;
import com.smeme.server.models.User;
import com.smeme.server.repositories.DiaryRepository;
import com.smeme.server.repositories.TopicRepository;
import com.smeme.server.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final UserRepository userRepository;
    private final TopicRepository topicRepository;

    @Transactional
    public DiaryCreateResponseDto createDiary(DiaryCreateRequestDto diaryRequestDto) {
        User user = userRepository.findById(diaryRequestDto.userId())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 유저입니다."));

        Topic topic = topicRepository.findById(diaryRequestDto.topicId())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 랜덤 주제입니다."));

        Diary savedDiary = diaryRepository.save(diaryRequestDto.toEntity(user, topic));

        return DiaryCreateResponseDto.from(savedDiary);
    }
}

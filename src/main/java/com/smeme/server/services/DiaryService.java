package com.smeme.server.services;

import com.smeme.server.dtos.diary.DiaryCreateRequestDto;
import com.smeme.server.dtos.diary.DiaryCreateResponseDto;
import com.smeme.server.dtos.diary.DiaryLikeResponseDto;
import com.smeme.server.dtos.diary.DiaryPublicDetailFindResponseDto;
import com.smeme.server.dtos.diary.DiaryPublicFindResponseDto;
import com.smeme.server.models.Category;
import com.smeme.server.models.Diary;
import com.smeme.server.models.Like;
import com.smeme.server.models.Topic;
import com.smeme.server.models.User;
import com.smeme.server.repositories.CategoryRepository;
import com.smeme.server.repositories.DiaryRepository;
import com.smeme.server.repositories.LikeRepository;
import com.smeme.server.repositories.TopicRepository;
import com.smeme.server.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class DiaryService {

    private final UserService userService;
    private final DiaryRepository diaryRepository;
    private final UserRepository userRepository;
    private final TopicRepository topicRepository;
    private final CategoryRepository categoryRepository;
    private final LikeRepository likeRepository;

    @Transactional
    public DiaryCreateResponseDto createDiary(Long userId, DiaryCreateRequestDto diaryRequestDto) {
        Topic topic = topicRepository.findById(diaryRequestDto.topicId())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 랜덤 주제입니다."));

        User user = userService.getUser(userId);

        Diary savedDiary = diaryRepository.save(diaryRequestDto.toEntity(user, topic));

        return DiaryCreateResponseDto.from(savedDiary);
    }

    @Transactional(readOnly = true)
    public List<DiaryPublicFindResponseDto> findPublicDiaries(String categoryId, Long userId) {
        User user = userService.getUser(userId);

        List<DiaryPublicFindResponseDto> diaries = new ArrayList<>();

        if (Objects.isNull(categoryId)) {
            diaryRepository.findByIsPublicIsTrue()
                    .stream().filter((diary -> diary.getUser() != user))
                    .forEach((diary -> diaries.add(DiaryPublicFindResponseDto.from(diary, user))));
        }
        else {
            Category category = categoryRepository.findById(Long.parseLong(categoryId))
                    .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 카테고리 입니다."));

            List<Topic> topics = topicRepository.findByCategory(category);

            topics.forEach((topic -> diaryRepository.findByIsPublicIsTrueAndTopic(topic)
                        .stream().filter(diary -> diary.getUser() != user)
                        .forEach(diary -> diaries.add(DiaryPublicFindResponseDto.from(diary, user)))));
        }

        return diaries;
    }

    @Transactional(readOnly = true)
    public DiaryPublicDetailFindResponseDto findPublicDiaryById(Long diaryId, Long userId) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 일기입니다."));

        User user = userService.getUser(userId);

        return DiaryPublicDetailFindResponseDto.from(diary, user);
    }

    @Transactional
    public DiaryLikeResponseDto likeDiary(Long diaryId, Long userId) {
        User user = userService.getUser(userId);

        Diary diary = diaryRepository.findById(diaryId)
            .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 일기입니다."));

        boolean hasLike = likeRepository.existsByUserAndDiary(user, diary);

        if (hasLike) {
            likeRepository.deleteByUserAndDiary(user, diary);

            return DiaryLikeResponseDto.builder()
                .hasLike(false)
                .build();
        } else {
            Like like = Like.builder()
                .diary(diary)
                .user(user)
                .build();

            likeRepository.save(like);

            return DiaryLikeResponseDto.builder()
                .hasLike(true)
                .build();
        }
    }
}

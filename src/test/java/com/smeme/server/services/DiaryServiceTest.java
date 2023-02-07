package com.smeme.server.services;

import com.smeme.server.dtos.diary.DiaryCreateRequestDto;
import com.smeme.server.dtos.diary.DiaryCreateResponseDto;
import com.smeme.server.models.*;
import com.smeme.server.repositories.DiaryRepository;
import com.smeme.server.repositories.TopicRepository;
import com.smeme.server.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DiaryServiceTest {

    @InjectMocks
    private DiaryService diaryService;

    @Mock
    private DiaryRepository diaryRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private TopicRepository topicRepository;

    private final User user = new User();
    private final Category category = Category.builder().content("category of topic").build();
    private final Topic topic = Topic.builder().category(category).build();

    @Test
    @DisplayName("Success - Diary 를 생성하면 생성한 id 반환")
    void createDiarySuccess() {
        // given
        DiaryCreateRequestDto diaryRequestDto = DiaryCreateRequestDto.builder()
                .content("I can write a diary in english!")
                .targetLang(TargetLang.en)
                .topicId(0L)
                .isPublic(true)
                .userId(0L)
                .build();

        Diary diary = diaryRequestDto.toEntity(user, topic);

        // when
        when(userRepository.findById(0L)).thenReturn(Optional.of(user));
        when(topicRepository.findById(0L)).thenReturn(Optional.of(topic));
        when(diaryRepository.save(any())).thenReturn(diary);

        DiaryCreateResponseDto diaryResponseDto = diaryService.createDiary(diaryRequestDto);

        // then
        verify(userRepository).findById(0L);
        verify(topicRepository).findById(0L);
        verify(diaryRepository).save(any());

        assertThat(diaryResponseDto).isEqualTo(DiaryCreateResponseDto.from(diary));
    }

    @Test
    @DisplayName("Fail - Diary 생성 중 유효하지 않은 userId 인 경우 EntityNotFoundException 발생")
    public void createDiaryFailByInvalidUser() {
        // given
        DiaryCreateRequestDto diaryRequestDto = DiaryCreateRequestDto.builder()
                .content("I can write a diary in english!")
                .targetLang(TargetLang.en)
                .topicId(0L)
                .isPublic(true)
                .userId(0L)
                .build();

        // when
        when(userRepository.findById(0L)).thenReturn(Optional.empty());

        // then
        assertThatThrownBy(() -> diaryService.createDiary(diaryRequestDto))
                .isExactlyInstanceOf(EntityNotFoundException.class)
                .hasMessage("존재하지 않는 유저입니다.");

        verify(userRepository).findById(0L);
    }

    @Test
    @DisplayName("Fail - Diary 생성 중 유효하지 않은 topicId 인 경우 EntityNotFoundException 발생")
    public void createDiaryFailByInvalidTopic() {
        // given
        DiaryCreateRequestDto diaryRequestDto = DiaryCreateRequestDto.builder()
                .content("I can write a diary in english!")
                .targetLang(TargetLang.en)
                .topicId(0L)
                .isPublic(true)
                .userId(0L)
                .build();

        // when
        when(userRepository.findById(0L)).thenReturn(Optional.of(user));
        when(topicRepository.findById(0L)).thenReturn(Optional.empty());

        // then
        assertThatThrownBy(() -> diaryService.createDiary(diaryRequestDto))
                .isExactlyInstanceOf(EntityNotFoundException.class)
                .hasMessage("존재하지 않는 랜덤 주제입니다.");

        verify(userRepository).findById(0L);
        verify(topicRepository).findById(0L);
    }

}
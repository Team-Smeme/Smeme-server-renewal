package com.smeem.diary.service;

import com.smeem.api.diary.service.DiaryCommandService;
import com.smeem.api.diary.service.dto.request.DiaryModifyServiceRequest;
import com.smeem.domain.diary.adapter.DiaryFinder;
import com.smeem.support.fixture.DiaryFixture;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class DiaryCommandServiceTest {

    @InjectMocks
    private DiaryCommandService diaryService;

    @Mock
    private DiaryFinder diaryFinder;

    @Test
    @DisplayName("[성공] 유효한 일기를 수정할 수 있다.")
    void modifyValidDiary() {
        // given
        val contentBeforeModified = "content";
        val contentAfterModified = "modifiedContent";
        val diary = DiaryFixture.diary().id(1L).content(contentBeforeModified).build();
        val request = new DiaryModifyServiceRequest(diary.getId(), contentAfterModified);

        doReturn(diary).when(diaryFinder).findById(diary.getId());

        // when
        diaryService.modifyDiary(request);

        // then
        assertThat(diary.getContent()).isEqualTo(contentAfterModified);
    }
}

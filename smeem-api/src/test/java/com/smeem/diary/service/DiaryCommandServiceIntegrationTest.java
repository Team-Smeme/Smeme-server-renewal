package com.smeem.diary.service;

import com.smeem.api.diary.service.DiaryCommandService;
import com.smeem.api.diary.service.dto.request.DiaryCreateServiceRequest;
import com.smeem.domain.diary.repository.DiaryRepository;
import com.smeem.domain.member.exception.MemberException;
import com.smeem.domain.member.model.Member;
import com.smeem.domain.member.repository.MemberRepository;
import com.smeem.support.ServiceIntegrationTest;
import com.smeem.support.fixture.MemberFixture;
import lombok.val;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static com.smeem.common.code.failure.MemberFailureCode.CANNOT_WRITE_DIARY;
import static org.assertj.core.api.Assertions.*;

public class DiaryCommandServiceIntegrationTest extends ServiceIntegrationTest {

    @Autowired
    private DiaryCommandService diaryService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private DiaryRepository diaryRepository;

    private final String MEMBER_EXCEPTION_PREFIX_MESSAGE = "[MemberException] : ";

    @Nested
    @DisplayName("일기 저장 테스트")
    class SaveTest {

        private Member member;
        private final String diaryContent = "test-diary-content";

        @BeforeEach
        void setUp() {
            memberRepository.deleteAllInBatch();
            diaryRepository.deleteAllInBatch();
            member = memberRepository.save(MemberFixture.member().build());
        }

        @AfterEach
        void tearDown() {
            diaryRepository.deleteAllInBatch();
            memberRepository.deleteAllInBatch();
        }

        @Test
        @Transactional
        @DisplayName("[성공] 오늘 일기를 작성하지 않은 회원은 일기를 작성할 수 있고, 연속 일기 작성 수가 업데이트된다.")
        void saveWithMemberNotCreateDiaryToday() {
            // given
            val request = new DiaryCreateServiceRequest(member.getId(), diaryContent, null);

            // when
            val result = diaryService.createDiary(request);

            // then
            assertThat(member.getDiaryComboCount()).isEqualTo(1);

            val diary = diaryRepository.findFirstByMemberOrderByCreatedAtDesc(member);
            assertThat(diary).isPresent();
            assertThat(diary.get().getId()).isEqualTo(result.diaryId());
            assertThat(diary.get().getCreatedAt().toLocalDate()).isEqualTo(LocalDate.now());
            assertThat(diary.get().getMember()).isEqualTo(member);
        }

        @Test
        @DisplayName("[예외] 오늘 일기를 작성한 회원은 일기를 작성할 수 없다.")
        void saveWithMemberCreateDiaryToday() {
            // given
            val request = new DiaryCreateServiceRequest(member.getId(), diaryContent, null);
            diaryService.createDiary(request);

            // when & then
            assertThatThrownBy(() -> diaryService.createDiary(request))
                    .isInstanceOf(MemberException.class)
                    .hasMessage(MEMBER_EXCEPTION_PREFIX_MESSAGE + CANNOT_WRITE_DIARY.getMessage());
        }
    }
}

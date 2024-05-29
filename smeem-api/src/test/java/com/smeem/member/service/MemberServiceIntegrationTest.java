package com.smeem.member.service;

import com.smeem.api.member.service.MemberService;
import com.smeem.api.member.service.dto.request.MemberVisitUpdateRequest;
import com.smeem.domain.member.model.Member;
import com.smeem.domain.member.repository.MemberRepository;
import com.smeem.domain.visit.adapter.VisitCounter;
import com.smeem.domain.visit.repository.VisitRepository;
import com.smeem.support.ServiceIntegrationTest;
import com.smeem.support.fixture.MemberFixture;
import jakarta.transaction.Transactional;
import lombok.val;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.*;

public class MemberServiceIntegrationTest extends ServiceIntegrationTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private VisitRepository visitRepository;

    @Autowired
    private VisitCounter visitCounter;

    @Nested
    @DisplayName("회원 방문 체크")
    class MemberVisitTest {

        private Member member;

        @BeforeEach
        public void setUp() {
            memberRepository.deleteAllInBatch();
            visitRepository.deleteAllInBatch();
            member = memberRepository.save(MemberFixture.member().build());
        }

        @Test
        @Transactional
        @DisplayName("[성공] 회원이 방문하면 하루에 한 번 이력이 남는다.")
        void createMemberVisitedHistoryTodayAtOnce() {
            // given
            val request = new MemberVisitUpdateRequest(member.getId());

            int initCount = visitCounter.countByMember(member);
            assertThat(initCount).isEqualTo(0);

            // when
            memberService.updateMemberVisit(request);
            memberService.updateMemberVisit(request);

            // then
            int visitCount = visitCounter.countByMember(member);
            assertThat(visitCount).isEqualTo(1);
        }
    }
}

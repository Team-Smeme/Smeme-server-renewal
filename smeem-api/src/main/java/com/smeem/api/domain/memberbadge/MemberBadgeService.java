package com.smeem.api.domain.memberbadge;

import com.smeem.api.domain.memberbadge.dto.response.MemberBadgesAcquireResponse;
import com.smeem.api.domain.badge.vo.Badge;
import com.smeem.api.domain.member.Member;
import com.smeem.api.domain.memberbadge.vo.MemberBadge;
import com.smeem.api.port.output.persistence.badge.BadgeFinder;
import com.smeem.api.port.output.persistence.diary.DiaryCounter;
import com.smeem.api.port.output.persistence.member.MemberFinder;
import com.smeem.api.port.output.persistence.memberbadge.MemberBadgeFinder;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MemberBadgeService {

    MemberBadgeFinder memberBadgeFinder;
    BadgeFinder badgeFinder;
    DiaryCounter diaryCounter;
    MemberFinder memberFinder;

    public MemberBadgesAcquireResponse getAll(long memberId) {
        val member = memberFinder.findById(memberId);
        val memberBadges = badgeFinder.findAllOrderById().stream()
                .map(badge -> MemberBadge.builder()
                        .member(member)
                        .badge(badge)
                        .hasBadge(memberBadgeFinder.isExistByMemberAndBadge(member.id(), badge.id()))
                        .remainingNumber(getRemainingNumber(member, badge))
                        .build())
                .toList();
        return MemberBadgesAcquireResponse.from(memberBadges);
    }

    private Integer getRemainingNumber(Member member, Badge badge) {
        return Objects.nonNull(badge.countForAchievement())
                ? badge.countForAchievement() - diaryCounter.countByMember(member.id())
                : null;
    }
}

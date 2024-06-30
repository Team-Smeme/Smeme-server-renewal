package com.smeem.api.domain.badge;

import com.smeem.api.domain.badge.vo.Badge;
import com.smeem.api.domain.badge.vo.BadgeType;
import com.smeem.api.domain.badge.dto.response.BadgesAcquireResponse;
import com.smeem.api.port.output.persistence.badge.BadgeFinder;
import com.smeem.api.port.output.persistence.member.MemberFinder;
import com.smeem.api.port.output.persistence.memberbadge.MemberBadgeFinder;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class BadgeService {

    private final BadgeFinder badgeFinder;
    private final MemberFinder memberFinder;
    private final MemberBadgeFinder memberBadgeFinder;

    public BadgesAcquireResponse getAll(long memberId) {
        val memberBadgeIds = memberBadgeFinder.findIdAllByMemberId(memberId);
        val typeToBadge = new LinkedHashMap<BadgeType, List<Badge>>();
        for (val badge : badgeFinder.findAllOrderById()) {
            typeToBadge.computeIfAbsent(badge.type(), x -> new ArrayList<>()).add(badge);
        }
        return BadgesAcquireResponse.from(typeToBadge, memberBadgeIds);
    }







    private Integer calculateRemainingNumber(Long badgeId, int count) {
        return switch (badgeId.intValue()) {
            case 2 -> 1 - count;
            case 3 -> 10 - count;
            case 4 -> 30 - count;
            case 5 -> 50 - count;
            default -> null;
        };
    }

    private BadgeGetServiceResponseV3 convertToBadgeGetServiceResponseV3(Badge badge, final long memberId) {
        val member = memberFinder.findById(memberId);
        val hasBadge = memberBadgeFinder.isExistByMemberAndBadge(member, badge);
        val memberDiaryCount = member.getDiaries().size();
        val remainingNumber = calculateRemainingNumber(badge.getId(), memberDiaryCount);
        return BadgeGetServiceResponseV3.of(badge, hasBadge, remainingNumber);
    }
}

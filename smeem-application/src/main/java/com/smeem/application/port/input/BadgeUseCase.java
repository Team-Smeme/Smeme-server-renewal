package com.smeem.application.port.input;

import com.smeem.application.port.input.dto.response.badge.RetrieveBadgesResponse;
import com.smeem.application.port.input.dto.response.badge.RetrieveMemberBadgesResponse;

public interface BadgeUseCase {
    RetrieveBadgesResponse retrieveBadges(long memberId);
    RetrieveMemberBadgesResponse retrieveMemberBadges(long memberId);
}

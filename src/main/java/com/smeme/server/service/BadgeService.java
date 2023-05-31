package com.smeme.server.service;

import com.smeme.server.dto.badge.BadgeListResponseDTO;
import com.smeme.server.dto.badge.BadgeResponseDTO;
import com.smeme.server.repository.BadgeRepository;
import com.smeme.server.repository.MemberBadgeRepository;
import com.smeme.server.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BadgeService {

    private final MemberBadgeRepository memberBadgeRepository;
    public BadgeListResponseDTO getBadgeList(Long memberId) {
        List<BadgeResponseDTO> badgeResponseDTOList =  memberBadgeRepository.findAllByMemberId(memberId)
                .stream()
                .map(memberBadge -> BadgeResponseDTO.of(memberBadge.getBadge().getId(), memberBadge.getBadge().getName(), memberBadge.getBadge().getImageUrl()))
                .toList();
        return new BadgeListResponseDTO(badgeResponseDTOList);
    }


}

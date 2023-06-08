package com.smeme.server.service;

import com.smeme.server.dto.badge.BadgeListResponseDTO;
import com.smeme.server.dto.badge.BadgeResponseDTO;
import com.smeme.server.repository.MemberBadgeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BadgeService {

    private final MemberBadgeRepository memberBadgeRepository;

    public BadgeListResponseDTO getBadgeList(Long memberId) {
        List<BadgeResponseDTO> badgeResponseDTOList =  memberBadgeRepository.findAllByMemberId(memberId)
                .stream()
                .map(BadgeResponseDTO::of)
                .toList();
        return BadgeListResponseDTO.of(badgeResponseDTOList);
    }
}

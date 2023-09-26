package com.smeme.server.service.auth;

import com.smeme.server.config.ValueConfig;
import com.smeme.server.config.jwt.JwtTokenProvider;
import com.smeme.server.config.jwt.UserAuthentication;
import com.smeme.server.dto.auth.beta.BetaSignInRequestDTO;
import com.smeme.server.dto.auth.beta.BetaTokenResponseDTO;
import com.smeme.server.dto.badge.BadgeResponseDTO;
import com.smeme.server.model.Member;
import com.smeme.server.model.badge.Badge;
import com.smeme.server.model.badge.MemberBadge;
import com.smeme.server.repository.MemberRepository;
import com.smeme.server.repository.badge.BadgeRepository;
import com.smeme.server.repository.badge.MemberBadgeRepository;
import com.smeme.server.util.message.ErrorMessage;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.smeme.server.model.LangType.*;
import static com.smeme.server.model.SocialType.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BetaAuthService {

    private final MemberRepository memberRepository;
    private final MemberBadgeRepository memberBadgeRepository;
    private final JwtTokenProvider tokenProvider;
    private final BadgeRepository badgeRepository;
    private final ValueConfig valueConfig;

    private static final Long BETA_USER_EXPIRED = 60 * 60 * 1000 * 24 * 21L;

    @Transactional
    public BetaTokenResponseDTO createBetaMember(BetaSignInRequestDTO request) {

        AtomicInteger atomicInteger = new AtomicInteger(1);
        Member betaMember = Member.builder()
                .social(BETA)
                .targetLang(en)
                .socialId("beta" + atomicInteger.getAndIncrement())
                .fcmToken(request.fcmToken())
                .build();
        memberRepository.save(betaMember);
        Authentication authentication = new UserAuthentication(betaMember.getId(), null, null);
        List<BadgeResponseDTO> badges = new ArrayList<>();
        badges.add(BadgeResponseDTO.of(addWelcomeBadge(betaMember)));
        return BetaTokenResponseDTO.of(tokenProvider.generateToken(authentication, BETA_USER_EXPIRED), badges);
    }

    private Badge addWelcomeBadge(Member member) {
        Badge badge = badgeRepository.findById(valueConfig.getWELCOME_BADGE_ID()).orElseThrow(
                () -> new EntityNotFoundException(ErrorMessage.EMPTY_BADGE.getMessage())
        );
        memberBadgeRepository.save(new MemberBadge(member, badge));
        return badge;
    }
}
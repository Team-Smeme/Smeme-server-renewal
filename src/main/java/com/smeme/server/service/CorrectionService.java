package com.smeme.server.service;

import static com.smeme.server.util.message.ErrorMessage.*;

import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smeme.server.dto.correction.CorrectionRequestDTO;
import com.smeme.server.dto.correction.CorrectionResponseDTO;
import com.smeme.server.model.Correction;
import com.smeme.server.model.Diary;
import com.smeme.server.model.Member;
import com.smeme.server.model.badge.Badge;
import com.smeme.server.repository.badge.BadgeRepository;
import com.smeme.server.repository.badge.MemberBadgeRepository;
import com.smeme.server.repository.MemberRepository;
import com.smeme.server.repository.correction.CorrectionRepository;
import com.smeme.server.repository.diary.DiaryRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CorrectionService {

    private final CorrectionRepository correctionRepository;
    private final DiaryRepository diaryRepository;
    private final MemberRepository memberRepository;
    private final BadgeRepository badgeRepository;
    private final MemberBadgeRepository memberBadgeRepository;
    private final BadgeService badgeService;

    @Transactional
    public CorrectionResponseDTO createCorrection(Long memberId, Long diaryId, CorrectionRequestDTO requestDTO) {
        Diary diary = getDiary(diaryId);
        correctionRepository.save(new Correction(requestDTO.sentence(), requestDTO.content(), diary));
        Member member = getMember(memberId);
        Badge badge = getCorrectionBadge(member);
        if (Objects.nonNull(badge) && !memberBadgeRepository.existsByMemberAndBadge(member, badge)) {
            badgeService.createMemberBadge(member, badge);
        }
        return new CorrectionResponseDTO(diaryId, badge);
    }

    private Badge getCorrectionBadge(Member member) {
        int count = correctionRepository.countCorrection(member);
        if (count == 5) {
            return getBadge(12L);
        } else if (count == 3) {
            return getBadge(11L);
        } else if (count == 1) {
            return getBadge(10L);
        }
        return null;
    }

    @Transactional
    public void deleteCorrection(Long correctionId) {
        Correction correction = getCorrection(correctionId);
        correction.deleteCorrection();
        correctionRepository.deleteById(correctionId);
    }

    @Transactional
    public void updateCorrection(Long correctionId, CorrectionRequestDTO requestDTO) {
        Correction correction = getCorrection(correctionId);
        correction.updateCorrection(requestDTO.content());
    }

    private Diary getDiary(Long diaryId) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new EntityNotFoundException(INVALID_DIARY.getMessage()));
        if (diary.isDeleted()) {
            throw new NoSuchElementException(DELETED_DIARY.getMessage());
        }
        return diary;
    }

    private Correction getCorrection(Long correctionId) {
        return correctionRepository.findById(correctionId)
                .orElseThrow(() -> new EntityNotFoundException(INVALID_CORRECTION.getMessage()));
    }

    private Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(INVALID_MEMBER.getMessage()));
    }

    private Badge getBadge(Long id) {
        return badgeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(INVALID_BADGE.getMessage()));
    }
}

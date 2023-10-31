package com.smeme.server.service;

import static com.smeme.server.util.message.ErrorMessage.*;
import static java.util.Objects.nonNull;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smeme.server.dto.correction.CorrectionRequestDTO;
import com.smeme.server.dto.correction.CorrectionResponseDTO;
import com.smeme.server.model.Correction;
import com.smeme.server.model.Diary;
import com.smeme.server.model.Member;
import com.smeme.server.model.badge.Badge;
import com.smeme.server.repository.correction.CorrectionRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CorrectionService {

    private final CorrectionRepository correctionRepository;

    private final BadgeService badgeService;
    private final DiaryService diaryService;
    private final MemberService memberService;

    @Transactional
    public CorrectionResponseDTO save(Long memberId, Long diaryId, CorrectionRequestDTO request) {
        Diary diary = diaryService.get(diaryId);
        correctionRepository.save(new Correction(request.sentence(), request.content(), diary));

        Member member = memberService.get(memberId);
        Badge countingBadge = obtainCountingBadge(member);
        if (nonNull(countingBadge) && member.hasNotBadge(countingBadge)) {
            badgeService.saveMemberBadge(member, countingBadge);
        }

        return CorrectionResponseDTO.of(diaryId, countingBadge);
    }

    @Transactional
    public void delete(Long id) {
        correctionRepository.deleteById(id);
    }

    @Transactional
    public void update(Long id, CorrectionRequestDTO request) {
        Correction correction = get(id);
        correction.updateContent(request.content());
    }

    @Transactional
    public void deleteAllByDiaryId(Long diaryId) {
        correctionRepository.deleteAllByDiaryId(diaryId);
    }


    protected void deleteCorrections(List<Correction> corrections) {
        correctionRepository.deleteAll(corrections);
    }

    private Badge obtainCountingBadge(Member member) {
        int count = correctionRepository.countByMember(member);
        return switch (count) {
            case 5 -> badgeService.get(12L);
            case 3 -> badgeService.get(11L);
            case 1 -> badgeService.get(10L);
            default -> null;
        };
    }

    private Correction get(Long id) {
        return correctionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(INVALID_CORRECTION.getMessage()));
    }
}

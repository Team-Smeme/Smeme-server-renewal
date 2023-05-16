package com.smeme.server.service;

import com.smeme.server.dto.member.UpdateMemberRequestDTO;
import com.smeme.server.model.Member;
import com.smeme.server.repository.MemberRepository;
import com.smeme.server.util.message.ErrorMessage;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public void updateMember(Long memberId, UpdateMemberRequestDTO dto) {
        checkMemberDuplicate(dto.username());
        Member member = getMemberById(memberId);
        member.updateUsername(dto.username());
        member.updateTermAccepted(dto.termAccepted());
    }

    private Member getMemberById(Long id) {
        return memberRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(ErrorMessage.EMPTY_MEMBER.getMessage()));
    }

    private void checkMemberDuplicate(String username) {
        if (memberRepository.existsByUsername(username)) {
            throw new EntityExistsException(ErrorMessage.DUPLICATE_USERNAME.getMessage());
        }
    }
}

package com.smeem.persistence.postgresql.adapter;

import com.smeem.application.port.output.persistence.DiaryPort;
import com.smeem.persistence.postgresql.persistence.repository.DiaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DiaryAdapter implements DiaryPort {
    private final DiaryRepository diaryRepository;

    @Override
    public int countByMember(long memberId) {
        return diaryRepository.countByMemberId(memberId);
    }
}

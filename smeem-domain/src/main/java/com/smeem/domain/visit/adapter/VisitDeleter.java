package com.smeem.domain.visit.adapter;

import com.smeem.domain.member.model.Member;
import com.smeem.domain.support.RepositoryAdapter;
import com.smeem.domain.visit.repository.VisitRepository;
import lombok.RequiredArgsConstructor;

@RepositoryAdapter
@RequiredArgsConstructor
public class VisitDeleter {

    private final VisitRepository visitRepository;

    public void deleteByMember(Member member) {
        visitRepository.deleteByMember(member);
    }
}

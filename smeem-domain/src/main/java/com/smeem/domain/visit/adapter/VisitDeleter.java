package com.smeem.domain.visit.adapter;

import com.smeem.domain.persistence.entity.MemberEntity;
import com.smeem.domain.support.RepositoryAdapter;
import com.smeem.domain.visit.repository.VisitRepository;
import lombok.RequiredArgsConstructor;

@RepositoryAdapter
@RequiredArgsConstructor
public class VisitDeleter {

    private final VisitRepository visitRepository;

    public void deleteByMember(MemberEntity member) {
        visitRepository.deleteByMember(member);
    }
}

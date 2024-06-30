package com.smeem.domain.visit.adapter;

import com.smeem.domain.persistence.entity.MemberEntity;
import com.smeem.domain.support.RepositoryAdapter;
import com.smeem.domain.visit.model.Visit;
import com.smeem.domain.visit.repository.VisitRepository;
import lombok.RequiredArgsConstructor;

@RepositoryAdapter
@RequiredArgsConstructor
public class VisitSaver {

    private final VisitRepository visitRepository;

    public void saveByMember(MemberEntity member) {
        visitRepository.save(Visit.builder().member(member).build());
    }
}

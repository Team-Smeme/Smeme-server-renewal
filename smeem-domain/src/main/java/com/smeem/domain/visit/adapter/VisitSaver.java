package com.smeem.domain.visit.adapter;

import com.smeem.domain.member.model.Member;
import com.smeem.domain.support.RepositoryAdapter;
import com.smeem.domain.visit.model.Visit;
import com.smeem.domain.visit.repository.VisitRepository;
import lombok.RequiredArgsConstructor;

@RepositoryAdapter
@RequiredArgsConstructor
public class VisitSaver {

    private final VisitRepository visitRepository;

    public void saveByMember(Member member) {
        visitRepository.save(new Visit(member));
    }
}

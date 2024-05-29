package com.smeem.domain.visit.adapter;

import com.smeem.domain.member.model.Member;
import com.smeem.domain.support.RepositoryAdapter;
import com.smeem.domain.visit.repository.VisitRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;

import java.time.LocalDate;

@RepositoryAdapter
@RequiredArgsConstructor
public class VisitFinder {

    private final VisitRepository visitRepository;

    public boolean isVisitedToday(Member member) {
        val today = LocalDate.now().atStartOfDay();
        val tomorrow = today.plusDays(1);
        return visitRepository.existsByMemberAndVisitedAtBetween(member, today, tomorrow);
    }
}

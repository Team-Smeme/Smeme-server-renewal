package com.smeem.domain.member.adapter.member;

import com.smeem.domain.member.model.Member;
import com.smeem.domain.support.RepositoryAdapter;
import lombok.RequiredArgsConstructor;

@RepositoryAdapter
@RequiredArgsConstructor
public class MemberUpdater {

    public void updateTermAccepted(final Member member, final boolean termAccepted) {
        member.updateTermAccepted(termAccepted);
    }
}

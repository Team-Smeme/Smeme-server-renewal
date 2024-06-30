package com.smeem.domain.member.adapter.member;

import com.smeem.domain.persistence.entity.MemberEntity;
import com.smeem.domain.support.RepositoryAdapter;
import lombok.RequiredArgsConstructor;

@RepositoryAdapter
@RequiredArgsConstructor
public class MemberUpdater {

    public void updateTermAccepted(final MemberEntity member, final boolean termAccepted) {
        member.updateTermAccepted(termAccepted);
    }
}

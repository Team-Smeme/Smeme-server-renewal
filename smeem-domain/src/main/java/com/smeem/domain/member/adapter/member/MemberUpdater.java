package com.smeem.domain.member.adapter.member;

import com.smeem.domain.member.model.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberUpdater {

    public void updateTermAccepted(final Member member, final boolean termAccepted) {
        member.updateTermAccepted(termAccepted);
    }
}

package com.smeme.server.repository.correction;

import com.smeme.server.model.Member;

public interface CorrectionCustomRepository {
    int countByMember(Member member);
}

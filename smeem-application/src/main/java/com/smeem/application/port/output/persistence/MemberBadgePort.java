package com.smeem.application.port.output.persistence;

import java.util.List;

public interface MemberBadgePort {
    List<Long> findIdsByMember(long memberId);
}

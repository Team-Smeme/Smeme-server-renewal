package com.smeem.application.port.output.external;

import com.smeem.application.domain.member.Member;
import com.smeem.application.domain.member.SocialType;

public interface SocialPort {
    Member.Social login(SocialType socialType, String accessToken);
}

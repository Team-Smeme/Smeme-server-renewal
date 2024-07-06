package com.smeem.application.port.output.oauth;

import com.smeem.application.domain.member.Member;
import com.smeem.application.domain.member.SocialType;

public interface OauthPort {
    Member.Social login(SocialType socialType, String accessToken);
}

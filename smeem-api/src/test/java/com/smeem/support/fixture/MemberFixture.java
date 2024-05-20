package com.smeem.support.fixture;

import com.smeem.domain.member.model.LangType;
import com.smeem.domain.member.model.Member;
import com.smeem.domain.member.model.SocialType;
import lombok.NoArgsConstructor;

import static com.smeem.domain.member.model.LangType.en;
import static com.smeem.domain.member.model.SocialType.KAKAO;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class MemberFixture {

    private Long id;
    private SocialType social = KAKAO;
    private String socialId = "test-social-id";
    private LangType targetLang = en;

    public static MemberFixture member() {
        return new MemberFixture();
    }

    public MemberFixture id(Long id) {
        this.id = id;
        return this;
    }

    public Member build() {
        return new Member(id, social, socialId, targetLang);
    }
}

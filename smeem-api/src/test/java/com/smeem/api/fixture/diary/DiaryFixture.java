package com.smeem.api.fixture.diary;


import com.smeem.api.fixture.member.MemberFixture;
import com.smeem.api.fixture.topic.TopicFixture;
import com.smeem.domain.diary.model.Diary;
import com.smeem.domain.member.model.LangType;

import static com.smeem.domain.member.model.LangType.en;

public class DiaryFixture {
    private static final Long DIARY_ID = 1L;
    private static final String DIARY_CONTENT = "";
    private static final LangType DIARY_LANG = en;
    private static final Boolean DIARY_IS_DELETED = false;
    private static final Boolean DIARY_IS_PUBLIC = true;

    public static Diary createDiary() {
        return Diary.builder()
                .member(MemberFixture.createMember())
                .content(DIARY_CONTENT)
                .topic(TopicFixture.createTopic())
                .build();
    }


}

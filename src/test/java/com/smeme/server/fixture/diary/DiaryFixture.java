package com.smeme.server.fixture.diary;

import com.smeme.server.fixture.member.MemberFixture;
import com.smeme.server.fixture.topic.TopicFixture;
import com.smeme.server.model.Diary;
import com.smeme.server.model.LangType;

public class DiaryFixture {
    private static final Long DIARY_ID = 1L;
    private static final String DIARY_CONTENT = "";
    private static final LangType DIARY_LANG = LangType.en;
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

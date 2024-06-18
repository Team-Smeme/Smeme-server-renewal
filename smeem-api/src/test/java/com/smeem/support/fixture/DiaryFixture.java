package com.smeem.support.fixture;

import com.smeem.domain.diary.model.Diary;
import com.smeem.domain.member.model.LangType;
import lombok.NoArgsConstructor;

import static com.smeem.domain.member.model.LangType.en;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class DiaryFixture {

    private Long id;
    private String content;
    private final LangType targetLang = en;
    private final boolean isPublic = false;

    public static DiaryFixture diary() {
        return new DiaryFixture();
    }

    public DiaryFixture id(Long id) {
        this.id = id;
        return this;
    }

    public DiaryFixture content(String content) {
        this.content = content;
        return this;
    }

    public Diary build() {
        return new Diary(id, content, targetLang, isPublic);
    }
}

package com.smeme.server.dtos.scrap;

import com.smeme.server.models.Diary;
import com.smeme.server.models.Scrap;
import com.smeme.server.models.User;
import lombok.NonNull;

public record ScrapRequestDto(
        @NonNull Long diaryId,
        @NonNull String paragraph
) {

    public Scrap toEntity(User user, Diary diary) {
        return Scrap.builder()
                .user(user)
                .diary(diary)
                .paragraph(this.paragraph)
                .build();
    }
}

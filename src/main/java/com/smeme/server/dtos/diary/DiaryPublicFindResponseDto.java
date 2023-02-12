package com.smeme.server.dtos.diary;

import com.smeme.server.models.Diary;
import com.smeme.server.models.History;
import com.smeme.server.models.Like;
import com.smeme.server.models.User;
import lombok.Builder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Builder
public record DiaryPublicFindResponseDto(
        Long diaryId,
        String content,
        int likeCnt,
        Long userId,
        String username,
        boolean isSeen,
        boolean hasLike,
        String createdAt
) {

    public static DiaryPublicFindResponseDto from(Diary diary, User user) {
        User diaryUser = diary.getUser();
        List<History> histories = diary.getHistories();
        List<Like> likes = diary.getLikes();

        return DiaryPublicFindResponseDto.builder()
                .diaryId(diary.getId())
                .content(diary.getContent())
                .likeCnt(likes.size())
                .userId(diaryUser.getId())
                .username(diaryUser.getUsername())
                .isSeen(getIsSeen(histories, user))
                .hasLike(getHasLike(likes, user))
                .createdAt(getCreatedAt(diary.getCreatedAt()))
                .build();
    }

    private static boolean getIsSeen(List<History> histories, User user) {
        return histories.stream().filter((history -> history.getUser() == user)).toList().size() > 0;
    }

    private static boolean getHasLike(List<Like> likes, User user) {
        return likes.stream().filter((like -> like.getUser() == user)).toList().size() > 0;
    }

    private static String getCreatedAt(LocalDateTime createdAt) {
        return createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

}

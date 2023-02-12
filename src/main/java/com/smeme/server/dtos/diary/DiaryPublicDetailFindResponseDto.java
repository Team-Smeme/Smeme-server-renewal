package com.smeme.server.dtos.diary;

import com.smeme.server.models.Diary;
import com.smeme.server.models.Like;
import com.smeme.server.models.Topic;
import com.smeme.server.models.User;
import lombok.Builder;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Builder
public record DiaryPublicDetailFindResponseDto(
        Long diaryId,
        String content,
        String category,
        String topic,
        int likeCnt,
        String createdAt,
        Long userId,
        String username,
        String bio,
        boolean hasLike
) {

    static public DiaryPublicDetailFindResponseDto from(Diary diary, User user) {
        Topic topic = diary.getTopic();
        List<Like> likes = diary.getLikes();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        User diaryUser = diary.getUser();

        List<Like> likeList = likes.stream().filter(like -> like.getUser() == user).toList();

        return DiaryPublicDetailFindResponseDto.builder()
                .diaryId(diary.getId())
                .content(diary.getContent())
                .category(topic.getCategory().getContent())
                .topic(topic.getContent())
                .likeCnt(likes.size())
                .createdAt(diary.getCreatedAt().format(formatter))
                .userId(diaryUser.getId())
                .username(diaryUser.getUsername())
                .bio(diaryUser.getBio())
                .hasLike(likeList.size() > 0)
                .build();
    }
}

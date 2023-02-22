package com.smeme.server.dtos.auth;

import com.smeme.server.dtos.diary.DiaryCreateResponseDto;
import com.smeme.server.models.Diary;
import com.smeme.server.models.Social;
import com.smeme.server.models.User;
import lombok.Builder;
import lombok.NonNull;

@Builder
public record AuthSignInRequestDto (
        @NonNull Social social
){
    public static AuthSignInRequestDto from(User user) {
        return AuthSignInRequestDto.builder().social(user.getSocial()).build();
    }

    public User toEntity() {
        return User.builder()
                .social(this.social)
                .build();
    }


}

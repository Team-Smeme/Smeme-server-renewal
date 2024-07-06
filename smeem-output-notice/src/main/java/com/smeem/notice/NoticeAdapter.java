package com.smeem.notice;

import com.smeem.application.port.output.notice.NoticePort;
import com.smeem.application.port.output.notice.NoticeType;
import com.smeem.notice.discord.DiscordNoticeService;
import com.smeem.notice.discord.dto.DiscordMessage;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NoticeAdapter implements NoticePort {
    private final DiscordNoticeService discordNoticeService;

    @Override
    public void noticeSignIn(String username, int memberCount) {
        val message = DiscordMessage.of(
                "전체 회원 수: " + memberCount,
                "새로운 회원 " + username + "님이 스밈에 가입했어요.",
                NoticeType.SIGN_IN);
        discordNoticeService.notice(message);
    }
}

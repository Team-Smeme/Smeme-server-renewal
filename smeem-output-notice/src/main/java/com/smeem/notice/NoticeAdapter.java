package com.smeem.notice;

import com.smeem.application.port.output.notice.NoticePort;
import com.smeem.application.port.output.notice.NoticeType;
import com.smeem.notice.discord.DiscordNoticeService;
import com.smeem.notice.discord.dto.DiscordMessage;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

@Component
@RequiredArgsConstructor
public class NoticeAdapter implements NoticePort {
    private final DiscordNoticeService discordNoticeService;

    @Override
    public void noticeSignIn(String username, int memberCount) {
        val message = DiscordMessage.of(
                "## 전체 회원 수: " + memberCount,
                "새로운 회원 " + username + "님이 스밈에 가입했어요.",
                NoticeType.SIGN_IN);
        discordNoticeService.notice(message);
    }

    @Override
    public void noticeError(Exception exception, WebRequest webRequest) {
        val message = DiscordMessage.of(
                "## 💥 500 Error",
                exception.getMessage() + "\n\n 🔗 " + getRequestUri(webRequest),
                NoticeType.ERROR
        );
        discordNoticeService.notice(message);
    }

    private static String getRequestUri(WebRequest webRequest) {
        val request = ((ServletWebRequest) webRequest).getRequest();
        val path = request.getMethod() + " " + request.getRequestURL();
        val queryString = request.getQueryString() != null ? "?" + request.getQueryString() : "";
        return path + queryString;
    }
}

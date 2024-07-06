package com.smeem.notice.discord.dto;

import com.smeem.application.port.output.notice.NoticeType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.val;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.io.PrintWriter;
import java.io.StringWriter;

@Builder(access = AccessLevel.PRIVATE)
public record DiscordErrorMessage(
        String title,
        String content,
        String requestUri,
        String requestedAt,
        NoticeType noticeType
) {

    public static DiscordErrorMessage of(
            String title,
            Exception exception,
            WebRequest webRequest,
            NoticeType noticeType
    ) {
        return DiscordErrorMessage.builder()
                .title(title)
                .content(getStackTrace(exception))
                .requestUri(getRequestUri(webRequest))
                .noticeType(noticeType)
                .build();
    }

    private static String getStackTrace(Exception exception) {
        val stringWriter = new StringWriter();
        exception.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    private static String getRequestUri(WebRequest webRequest) {
        val request = ((ServletWebRequest) webRequest).getRequest();
        val path = request.getMethod() + " " + request.getRequestURL();
        val queryString = request.getQueryString() != null ? "?" + request.getQueryString() : "";
        return path + queryString;
    }
}

package com.smeem.common.logger;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.val;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDate;

@Builder(access = AccessLevel.PRIVATE)
public record LoggingMessage(
        String title,
        String content,
        LocalDate sendAt,
        LoggerType noticeType
) {

    public static LoggingMessage of(String title, String content, LoggerType noticeType) {
        return LoggingMessage.builder()
                .title("# " + title)
                .content(content)
                .sendAt(LocalDate.now())
                .noticeType(noticeType)
                .build();
    }

    public static LoggingMessage signIn(String username, int memberCount) {
        return LoggingMessage.builder()
                .title("# 전체 회원 수: " + memberCount)
                .content("새로운 회원 " + username + "님이 가입했습니다.")
                .sendAt(LocalDate.now())
                .noticeType(LoggerType.SIGN_IN)
                .build();
    }

    public static LoggingMessage error(Exception exception, WebRequest webRequest) {
        return LoggingMessage.builder()
                .title("# 🧨 500 에러 발생")
                .content(exception.getMessage() + "\n\n 🔗 [요청 URI] " + getRequestUri(webRequest))
                .sendAt(LocalDate.now())
                .noticeType(LoggerType.ERROR)
                .build();
    }

    public static LoggingMessage withdraw(String withdrawType, String reason) {
        return LoggingMessage.builder()
                .title("# 💦 회원탈퇴")
                .content("기존 회원이 탈퇴했습니다."
                        + "\n탈퇴 사유: " + withdrawType
                        + "\n상세 의견: " + reason)
                .sendAt(LocalDate.now())
                .noticeType(LoggerType.WITHDRAW)
                .build();
    }

    public static LoggingMessage coachingSurvey(boolean isSatisfied, String dissatisfactionTypes, String reason) {
        return LoggingMessage.builder()
                .title((isSatisfied ? "# 👍 좋은" : "# 👎 아쉬운") + " 만족도 조사 결과")
                .content((isSatisfied ? "" : "\n불만족 사유: " + dissatisfactionTypes)
                        + "\n상세 이유: " + reason)
                .sendAt(LocalDate.now())
                .noticeType(LoggerType.SURVEY)
                .build();
    }

    private static String getRequestUri(WebRequest webRequest) {
        val request = ((ServletWebRequest) webRequest).getRequest();
        val path = request.getMethod() + " " + request.getRequestURL();
        val queryString = request.getQueryString() != null ? "?" + request.getQueryString() : "";
        return path + queryString;
    }
}

package com.smeem.application.port.output.notice;

import org.springframework.web.context.request.WebRequest;

public interface NoticePort {
    void noticeSignIn(String username, int memberCount);
    void noticeError(Exception exception, WebRequest request);
}

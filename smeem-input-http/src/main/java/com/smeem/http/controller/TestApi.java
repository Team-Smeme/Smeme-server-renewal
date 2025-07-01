package com.smeem.http.controller;

import java.security.Principal;

import com.smeem.application.domain.generic.SmeemMessage;
import com.smeem.application.port.output.notification.NotificationPort;
import com.smeem.common.util.SmeemConverter;
import com.smeem.http.controller.dto.SmeemResponse;
import com.smeem.http.controller.docs.TestApiDocs;
import com.smeem.http.controller.scrap.OpenGraph;
import com.smeem.http.controller.scrap.OpenGraphVO;
import lombok.val;
import org.apache.commons.text.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/test")
public class TestApi implements TestApiDocs {
    private static final Logger log = LoggerFactory.getLogger(TestApi.class);
    private final NotificationPort notificationPort;
    private final SmeemConverter smeemConverter;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public String test() {
        return "server connect";
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @GetMapping("/alarm")
    public SmeemResponse<?> pushAlarm(Principal principal) {
        val memberId = smeemConverter.toMemberId(principal);
        notificationPort.test(memberId);
        return SmeemResponse.of(SmeemMessage.TEST);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/scrap")
    public SmeemResponse<?> scrap(@RequestParam String url) {
        OpenGraphVO ogVO = null;

        try {
            OpenGraph page = new OpenGraph(url, true);

            ogVO = new OpenGraphVO();
            ogVO.setTitle(getContent(page, "title"));
            ogVO.setDescription(getContent(page, "description"));
            ogVO.setImage(getContent(page, "image"));
            ogVO.setType(getContent(page, "type"));
            ogVO.setUrl(getContent(page, "url"));
            ogVO.setAuthor(getContent(page, "article:author"));

            return SmeemResponse.of(ogVO, SmeemMessage.TEST);
        } catch (Exception e) {
            log.error("failed to scrap");
            return SmeemResponse.of(ogVO, SmeemMessage.TEST);
        }
    }

    private String getContent(OpenGraph page, String propertyName) {
        try {
            return StringEscapeUtils.unescapeHtml4(page.getContent(propertyName));
        } catch (NullPointerException e) {
            return "태그 없음";
        }
    }
}

package com.smeem.output.web.scrap;

import com.smeem.application.port.output.web.scrap.ScrapInfo;
import com.smeem.application.port.output.web.scrap.ScrapPort;
import com.smeem.common.exception.SmeemException;
import com.smeem.output.web.scrap.openapi.OpenGraph;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.smeem.common.exception.ExceptionCode.SERVICE_AVAILABLE;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScrapAdapter implements ScrapPort {

    private final OpenGraph openGraph;

    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String IMAGE = "image";
    private static final String TYPE = "type";
    private static final String URL = "url";
    private static final String ARTICLE_AUTHOR = "article:author";
    private static final int THUMBNAIL_IMAGE_URL_MAX_SIZE = 1500;

    @NonNull
    public ScrapInfo scrap(@NonNull String url) {
        try {
            openGraph.loading(url, true);
            return ScrapInfo.builder()
                    .title(openGraph.getContent(TITLE))
                    .description(openGraph.getContent(DESCRIPTION))
                    .image(getUrl(openGraph.getContent(IMAGE), THUMBNAIL_IMAGE_URL_MAX_SIZE))
                    .type(openGraph.getContent(TYPE))
                    .url(openGraph.getContent(URL))
                    .author(openGraph.getContent(ARTICLE_AUTHOR))
                    .build();
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new SmeemException(SERVICE_AVAILABLE, exception.getMessage());
        }
    }

    private String getUrl(String targetUrl, int maxSize) {
        return Optional.ofNullable(targetUrl).filter(url -> url.length() <= maxSize).orElse(null);
    }
}

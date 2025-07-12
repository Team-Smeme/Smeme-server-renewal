package com.smeem.output.web.scrap;

import com.smeem.application.port.output.web.scrap.ScrapInfo;
import com.smeem.application.port.output.web.scrap.ScrapPort;
import com.smeem.common.exception.SmeemException;
import com.smeem.output.web.scrap.openapi.OpenGraph;
import io.micrometer.common.util.StringUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

import static com.smeem.common.exception.ExceptionCode.SERVICE_AVAILABLE;
import static java.nio.charset.StandardCharsets.UTF_8;

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
    private static final String TINY_URL_LINK = "https://tinyurl.com/api-create.php?url=";

    @NonNull
    public ScrapInfo scrap(@NonNull String url) {
        try {
            openGraph.loading(url, true);
            return ScrapInfo.builder()
                    .title(openGraph.getContent(TITLE))
                    .description(openGraph.getContent(DESCRIPTION))
                    .image(shorteningUrl(openGraph.getContent(IMAGE)))
                    .type(openGraph.getContent(TYPE))
                    .url(openGraph.getContent(URL))
                    .author(openGraph.getContent(ARTICLE_AUTHOR))
                    .build();
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new SmeemException(SERVICE_AVAILABLE, exception.getMessage());
        }
    }

    public String shorteningUrl(String targetUrl) {
        if (StringUtils.isEmpty(targetUrl)) {
            return null;
        }

        try {
            URL url = new URL(TINY_URL_LINK + URLEncoder.encode(targetUrl, UTF_8));
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            String shortUrl = br.readLine();
            br.close();
            return shortUrl;
        } catch (Exception exception) {
            return null;
        }
    }
}

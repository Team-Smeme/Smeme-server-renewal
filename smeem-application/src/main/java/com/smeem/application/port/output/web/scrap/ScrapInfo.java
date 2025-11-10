package com.smeem.application.port.output.web.scrap;

import lombok.Builder;

@Builder
public record ScrapInfo(
        String title,
        String description,
        String type,
        String url,
        String image,
        String author
) {
}

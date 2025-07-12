package com.smeem.output.web.scrap.openapi;

import lombok.Builder;

@Builder
public record MetaElement(
        String namespace,
        String property,
        String content
) {
}

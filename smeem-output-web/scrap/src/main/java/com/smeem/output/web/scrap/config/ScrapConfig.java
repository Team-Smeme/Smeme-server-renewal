package com.smeem.output.web.scrap.config;

import org.htmlcleaner.HtmlCleaner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ScrapConfig {

    @Bean
    public HtmlCleaner htmlCleaner() {
        return new HtmlCleaner();
    }
}

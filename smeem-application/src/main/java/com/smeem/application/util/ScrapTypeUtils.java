package com.smeem.application.util;

import com.smeem.application.domain.bookmark.model.ScrapType;
import io.micrometer.common.util.StringUtils;

import java.util.Map;
import java.util.regex.Pattern;

public class ScrapTypeUtils {

    private static final Map<String, ScrapType> REGEX_MAP = Map.of(
            "https:\\/\\/www\\.instagram\\.com\\/(?:[^/]+\\/)?p\\/.*", ScrapType.POST,
            "https:\\/\\/www\\.instagram\\.com\\/(?:[^/]+\\/)?reels?\\/.*", ScrapType.REELS
    );

    public static ScrapType getType(String url) {
       if (StringUtils.isEmpty(url)) {
            return null;
        }

        return REGEX_MAP.entrySet()
                .stream()
                .filter(entry -> Pattern.matches(entry.getKey(), url))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(null);
    }
}

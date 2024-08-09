package com.smeem.application.domain.topic;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Topic {
    Long id;
    TopicCategory topicCategory;
    String content;
}

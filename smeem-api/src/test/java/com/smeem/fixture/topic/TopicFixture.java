package com.smeem.fixture.topic;

import com.smeme.server.dto.topic.TopicResponseDTO;
import com.smeme.server.model.topic.Category;
import com.smeme.server.model.topic.Topic;

public class TopicFixture {

    private static final Category TOPIC_CATEGORY = Category.DEVELOP;
    private static final String TOPIC_CONTENT = "com.smeem.test com.smeem.topic content";


    public static Topic createTopic() {
        return Topic.builder()
                .category(TOPIC_CATEGORY)
                .content(TOPIC_CONTENT)
                .build();
    }

    public static TopicResponseDTO createTopicResponseDTO() {
        return TopicResponseDTO.of(createTopic());
    }
}

package com.smeem.api.fixture.topic;


import com.smeem.api.topic.controller.dto.response.TopicResponseDTO;
import com.smeem.domain.topic.model.Category;
import com.smeem.domain.topic.model.Topic;

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

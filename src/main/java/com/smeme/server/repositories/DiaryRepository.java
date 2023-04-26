package com.smeme.server.repositories;

import com.smeme.server.model.Diary;
import com.smeme.server.model.topic.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {
    List<Diary> findByIsPublicIsTrue();
    List<Diary> findByIsPublicIsTrueAndTopic(Topic topic);
}

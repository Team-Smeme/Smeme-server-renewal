package com.smeme.server.repositories;

import com.smeme.server.models.Category;
import com.smeme.server.models.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
    List<Topic> findByCategory(Category category);
}

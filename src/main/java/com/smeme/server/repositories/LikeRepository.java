package com.smeme.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smeme.server.models.Diary;
import com.smeme.server.models.Like;
import com.smeme.server.models.User;

public interface LikeRepository extends JpaRepository<Like, Long> {

	boolean existsByUserAndDiary(User user, Diary diary);
	void deleteByUserAndDiary(User user, Diary diary);
}

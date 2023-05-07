package com.smeme.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smeme.server.model.Correction;
import com.smeme.server.model.Diary;

public interface CorrectionRepository extends JpaRepository<Correction, Long> {
	List<Correction> findByDiaryOrderByIdDesc(Diary diary);
}

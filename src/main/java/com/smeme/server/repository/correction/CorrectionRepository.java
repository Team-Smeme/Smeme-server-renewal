package com.smeme.server.repository.correction;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smeme.server.model.Correction;
import com.smeme.server.model.Diary;

public interface CorrectionRepository extends JpaRepository<Correction, Long>, CorrectionCustomRepository {
	List<Correction> findByDiaryOrderByIdDesc(Diary diary);

	void deleteAllByDiaryId(Long id);
}

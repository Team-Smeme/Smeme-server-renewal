package com.smeme.server.service;

import static com.smeme.server.util.message.ErrorMessage.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import com.smeme.server.dto.correction.CorrectionRequestDTO;
import com.smeme.server.model.Correction;
import com.smeme.server.model.Diary;
import com.smeme.server.repository.CorrectionRepository;
import com.smeme.server.repository.diary.DiaryRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CorrectionService {

	private final CorrectionRepository correctionRepository;
	private final DiaryRepository diaryRepository;

	@Transactional
	public void createCorrection(Long diaryId, CorrectionRequestDTO requestDTO) {
		Diary diary = getDiary(diaryId);
		correctionRepository.save(new Correction(requestDTO.sentence(), requestDTO.content(), diary));
	}

	private Diary getDiary(Long diaryId) {
		Diary diary = diaryRepository.findById(diaryId)
			.orElseThrow(() -> new EntityNotFoundException(INVALID_DIARY.getMessage()));
		if (diary.isDeleted()) {
			throw new NotFoundException(DELETED_DIARY.getMessage());
		}
		return diary;
	}
}
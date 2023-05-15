package com.smeme.server.model;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;
import static java.util.Objects.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Correction {

	@Id @GeneratedValue(strategy = IDENTITY)
	@Column(name = "correction_id")
	private Long id;

	private String beforeSentence;

	private String afterSentence;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "diary_id")
	private Diary diary;

	public Correction(String beforeSentence, String afterSentence, Diary diary) {
		this.beforeSentence = beforeSentence;
		this.afterSentence = afterSentence;
		setDiary(diary);
	}

	public void deleteCorrection() {
		if (nonNull(this.diary)) {
			this.diary.getCorrections().remove(this);
		}
	}

	public void updateCorrection(String content) {
		this.afterSentence = content;
	}

	private void setDiary(Diary diary) {
		if (nonNull(this.diary)) {
			this.diary.getCorrections().remove(this);
		}
		this.diary = diary;
		diary.getCorrections().add(this);
	}
}

package com.smeme.server.model.training;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;
import static java.util.Objects.*;

import com.smeme.server.model.Member;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class TrainingTime {

	@Id @GeneratedValue(strategy = IDENTITY)
	private Long id;

	@Enumerated(value = EnumType.STRING)
	private DayType day;

	@Min(value = 1, message = "시(hour)는 1 이상이어야 합니다.")
	@Max(value = 24, message = "시(hour)는 24 이하여야 합니다.")
	private int hour;

	@Min(value = 0, message = "분(minute)은 0 이상이어야 합니다.")
	@Max(value = 59, message = "분(minute)은 59 이하이어야 합니다.")
	private int minute;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@Builder
	public TrainingTime(DayType day, int hour, int minute, Member member) {
		this.day = day;
		this.hour = hour;
		this.minute = minute;
		setMember(member);
	}

	private void setMember(Member member) {
		if (nonNull(this.member)) {
			this.member.getTrainingTimes().remove(this);
		}
		this.member = member;
		member.getTrainingTimes().add(this);
	}
}

package com.dnd.demo.domain.advertisement.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Advertisement {

	@Id
	private Long projectId;

	private String startDate;
	private String endDate;

	public static Advertisement create(Long projectId, String startDate, String endDate) {
		return Advertisement.builder()
			.projectId(projectId)
			.startDate(startDate)
			.endDate(endDate)
			.build();
	}
}

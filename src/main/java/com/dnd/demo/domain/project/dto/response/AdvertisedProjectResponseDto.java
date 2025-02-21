package com.dnd.demo.domain.project.dto.response;

import com.dnd.demo.domain.advertisement.entity.Advertisement;
import com.dnd.demo.domain.project.entity.Project;
import com.dnd.demo.domain.project.enums.Job;
import com.dnd.demo.domain.project.enums.Level;

public record AdvertisedProjectResponseDto(
	Long projectId,
	String title,
	String description,
	String dueDate,
	Integer favoriteCount,
	Integer participantCount,
	String thumbnailImgUrl,
	String logoImgUrl,
	Job targetJob,
	Level targetLevel,
	String startDate,
	String endDate
) {
	public static AdvertisedProjectResponseDto from(Advertisement advertisement, Project project) {
		return new AdvertisedProjectResponseDto(
			project.getProjectId(),
			project.getTitle(),
			project.getDescription(),
			project.getDueDate(),
			project.getFavoriteCount(),
			project.getParticipantCount(),
			project.getThumbnailImgUrl(),
			project.getLogoImgUrl(),
			project.getTargetJob(),
			project.getTargetLevel(),
			advertisement.getStartDate(),
			advertisement.getEndDate()
		);
	}
}


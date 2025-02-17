package com.dnd.demo.domain.project.dto.response;

import com.dnd.demo.domain.project.entity.Project;

public record ProjectListResponseDto(
	Long projectId,
	String title,
	String description,
	String dueDate,
	Integer favoriteCount,
	Integer participantCount,
	String thumbnailImgUrl
) {
	public static ProjectListResponseDto from(Project project) {
		return new ProjectListResponseDto(
			project.getProjectId(),
			project.getTitle(),
			project.getDescription(),
			project.getDueDate(),
			project.getFavoriteCount(),
			project.getParticipantCount(),
			project.getThumbnailImgUrl()
		);
	}
}

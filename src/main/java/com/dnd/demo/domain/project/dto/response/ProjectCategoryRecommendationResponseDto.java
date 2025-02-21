package com.dnd.demo.domain.project.dto.response;

import com.dnd.demo.domain.project.entity.Project;
import com.dnd.demo.domain.project.enums.Job;
import com.dnd.demo.domain.project.enums.Level;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProjectCategoryRecommendationResponseDto {

	private Long projectId;
	private String title;
	private Level targetLevel;
	private Job targetJob;
	private String description;
	private String thumbnailImgUrl;
	private String logoImgUrl;

	public static ProjectCategoryRecommendationResponseDto from(Project project) {
		return ProjectCategoryRecommendationResponseDto.builder()
			.projectId(project.getProjectId())
			.title(project.getTitle())
			.targetLevel(project.getTargetLevel())
			.targetJob(project.getTargetJob())
			.description(project.getDescription())
			.thumbnailImgUrl(project.getThumbnailImgUrl())
			.logoImgUrl(project.getLogoImgUrl())
			.build();
	}
}

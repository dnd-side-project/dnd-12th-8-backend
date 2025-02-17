package com.dnd.demo.domain.project.dto.response;

import com.dnd.demo.domain.project.entity.ProjectDetail;
import com.dnd.demo.domain.project.enums.DetailType;

public record ProjectDetailResponse(
	DetailType type,
	String detailContent
) {
	public static ProjectDetailResponse from(ProjectDetail projectDetail) {
		return new ProjectDetailResponse(projectDetail.getType(), projectDetail.getDetailContent());
	}
}

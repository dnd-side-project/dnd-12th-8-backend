package com.dnd.demo.domain.project.dto.request;

import com.dnd.demo.domain.project.entity.ProjectDetail;
import com.dnd.demo.domain.project.enums.DetailType;

import jakarta.validation.constraints.Size;

public record ProjectDetailRequest(

	DetailType type,

	@Size(max = 3000, message = "상세 정보 내용은 최대 3000자까지 입력 가능합니다.")
	String detailContent

) {
	public ProjectDetail toEntity(Long projectId) {
		return ProjectDetail.builder()
			.projectId(projectId)
			.type(type)
			.detailContent(detailContent)
			.build();
	}
}

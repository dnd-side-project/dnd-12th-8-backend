package com.dnd.demo.domain.project.dto.request;

import java.util.List;

import com.dnd.demo.domain.project.entity.ProjectCategory;
import com.dnd.demo.domain.project.enums.PlatformType;

import jakarta.validation.constraints.*;

public record PlatformCategoryRequest(
	PlatformType platform,
	List<Long> categoryIds
) {
	public List<ProjectCategory> toEntities(Long projectId) {
		return categoryIds.stream()
			.map(categoryId -> ProjectCategory.create(projectId, categoryId))
			.toList();
	}
}

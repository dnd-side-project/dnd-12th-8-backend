package com.dnd.demo.domain.project.dto.response;

import java.util.Collections;
import java.util.List;

import com.dnd.demo.domain.project.entity.Category;
import com.dnd.demo.domain.project.enums.PlatformType;
import com.dnd.demo.global.exception.CustomException;
import com.dnd.demo.global.exception.ErrorCode;

public record PlatformCategoryResponse(
	PlatformType platform,
	List<String> categoryNames
) {
	public static PlatformCategoryResponse from(List<Category> categories) {
		if (categories == null || categories.isEmpty()) {
			return new PlatformCategoryResponse(null, Collections.emptyList());
		}

		PlatformType platformType = categories.get(0).getType();
		List<String> categoryNames = categories.stream()
			.map(Category::getCategoryName)
			.toList();

		return new PlatformCategoryResponse(platformType, categoryNames);
	}

}

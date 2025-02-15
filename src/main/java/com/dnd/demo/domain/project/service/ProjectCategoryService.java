package com.dnd.demo.domain.project.service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dnd.demo.domain.project.dto.request.PlatformCategoryRequest;
import com.dnd.demo.domain.project.entity.Project;
import com.dnd.demo.domain.project.entity.ProjectCategory;
import com.dnd.demo.domain.project.repository.ProjectCategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectCategoryService {

	private final ProjectCategoryRepository projectCategoryRepository;
	private final CategoryService categoryService;

	@Transactional
	public void saveByProjectId(Project project, PlatformCategoryRequest platformCategoryRequest) {
		List<Long> newCategoryIds = Objects.requireNonNullElse(platformCategoryRequest.categoryIds(), Collections.emptyList());
		// categoryService.validateCategoryIds(platformCategoryRequest.categoryIds());

		List<ProjectCategory> newCategories = newCategoryIds.stream()
			.map(categoryId -> ProjectCategory.create(project.getId(), categoryId))
			.toList();

		projectCategoryRepository.saveAll(newCategories);
	}

	public void deleteByProjectId(Project project) {
		projectCategoryRepository.deleteByProjectId(project.getId());
	}
}

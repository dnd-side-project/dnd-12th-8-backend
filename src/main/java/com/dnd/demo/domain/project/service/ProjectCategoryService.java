package com.dnd.demo.domain.project.service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dnd.demo.domain.project.dto.request.PlatformCategoryRequest;
import com.dnd.demo.domain.project.dto.response.PlatformCategoryResponse;
import com.dnd.demo.domain.project.entity.Category;
import com.dnd.demo.domain.project.entity.Project;
import com.dnd.demo.domain.project.entity.ProjectCategory;
import com.dnd.demo.domain.project.enums.PlatformType;
import com.dnd.demo.domain.project.repository.CategoryRepository;
import com.dnd.demo.domain.project.repository.ProjectCategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectCategoryService {

	private final ProjectCategoryRepository projectCategoryRepository;
	private final CategoryService categoryService;
	private final CategoryRepository categoryRepository;

	@Transactional
	public void saveByProjectId(Project project, PlatformCategoryRequest platformCategoryRequest) {
		List<Long> newCategoryIds = Objects.requireNonNullElse(platformCategoryRequest.categoryIds(), Collections.emptyList());
		// categoryService.validateCategoryIds(platformCategoryRequest.categoryIds());

		List<ProjectCategory> newCategories = newCategoryIds.stream()
			.map(categoryId -> ProjectCategory.create(project.getProjectId(), categoryId))
			.toList();

		projectCategoryRepository.saveAll(newCategories);
	}

	public void deleteByProjectId(Project project) {
		projectCategoryRepository.deleteByProjectId(project.getProjectId());
	}

	@Transactional(readOnly = true)
	public PlatformCategoryResponse getPlatformCategoryByProjectId(Long projectId) {
		List<Long> categoryIds = projectCategoryRepository.findByProjectId(projectId)
			.stream()
			.map(ProjectCategory::getCategoryId)
			.toList();

		List<Category> categories = categoryService.getCategoriesByIds(categoryIds);

		return PlatformCategoryResponse.from(categories);
	}

	@Transactional(readOnly = true)
	public PlatformCategoryResponse getCategoryInfoByProjectId(Long projectId) {
		List<Long> categoryIds = projectCategoryRepository.findCategoryIdsByProjectId(projectId);
		List<Category> categories = categoryRepository.findByCategoryIdIn(categoryIds);
		return PlatformCategoryResponse.from(categories);
	}

	@Transactional(readOnly = true)
	public List<Long> getCategoryIdsByProjectId(Long projectId) {
		return projectCategoryRepository.findCategoryIdsByProjectId(projectId);
	}
}

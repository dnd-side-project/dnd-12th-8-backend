package com.dnd.demo.domain.project.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dnd.demo.domain.project.entity.Category;
import com.dnd.demo.domain.project.repository.CategoryRepository;
import com.dnd.demo.global.exception.CustomException;
import com.dnd.demo.global.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {

	private final CategoryRepository categoryRepository;

	@Transactional(readOnly = true)
	public void validateCategoryIds(List<Long> categoryIds) {
		//TODO: 웹,앱 검증 유무 추가

		List<Long> existingCategoryIds = categoryRepository.findAllById(categoryIds)
			.stream()
			.map(Category::getCategoryId)
			.toList();

		List<Long> invalidCategoryIds = categoryIds.stream()
			.filter(id -> !existingCategoryIds.contains(id))
			.toList();

		if (!invalidCategoryIds.isEmpty()) {
			throw new CustomException(ErrorCode.CATEGORY_NOT_FOUND);
		}
	}

	@Transactional(readOnly = true)
	public List<Category> getCategoriesByIds(List<Long> categoryIds) {
		return Optional.ofNullable(categoryRepository.findByCategoryIdIn(categoryIds))
			.orElse(Collections.emptyList());
	}

}

package com.dnd.demo.domain.project.repository;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.dnd.demo.domain.project.entity.QCategory;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CategoryQueryDslRepositoryImpl implements CategoryQueryDslRepository {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<Long> findRelatedCategoryIds(List<Long> selectedCategoryIds) {
		//선택한 categoryId가 가진 categoryName 찾기
		List<String> categoryNames = queryFactory
			.select(QCategory.category.categoryName)
			.from(QCategory.category)
			.where(QCategory.category.categoryId.in(selectedCategoryIds))
			.fetch();

		if (categoryNames.isEmpty()) return Collections.emptyList();

		// 같은 categoryName을 가진 모든 categoryId 조회
		return queryFactory
			.select(QCategory.category.categoryId)
			.from(QCategory.category)
			.where(QCategory.category.categoryName.in(categoryNames))
			.fetch();
	}
}

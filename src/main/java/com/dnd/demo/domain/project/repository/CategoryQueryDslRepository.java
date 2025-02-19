package com.dnd.demo.domain.project.repository;

import java.util.List;

public interface CategoryQueryDslRepository {
	List<Long> findRelatedCategoryIds(List<Long> selectedCategoryIds);
}

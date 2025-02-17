package com.dnd.demo.domain.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dnd.demo.domain.project.entity.ProjectCategory;

@Repository
public interface ProjectCategoryRepository extends JpaRepository<ProjectCategory, Long> {
	List<ProjectCategory> findByProjectId(Long id);

	void deleteByProjectId(Long id);
}

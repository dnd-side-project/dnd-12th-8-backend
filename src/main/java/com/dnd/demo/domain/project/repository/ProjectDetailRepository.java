package com.dnd.demo.domain.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dnd.demo.domain.project.entity.ProjectDetail;

@Repository
public interface ProjectDetailRepository extends JpaRepository<ProjectDetail, Long> {
	void deleteByProjectId(Long id);
}


package com.dnd.demo.domain.project.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dnd.demo.domain.project.entity.Project;
import com.dnd.demo.domain.project.enums.Job;

public interface ProjectQueryDslRepository {
	List<Project> findFavoriteProjectListByProjectId(String memberId);

	Page<Project> findPopularProjects(Pageable pageable);

	Page<Project> findRecommendedProjects(String memberId, Pageable pageable);

	Page<Project> searchProjects(String query, Job job, List<Long> categoryIds, Pageable pageable);

	Optional<Project> findLatestTemporaryProjectByMemberId(String memberId);

}

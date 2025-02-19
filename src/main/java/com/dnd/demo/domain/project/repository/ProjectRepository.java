package com.dnd.demo.domain.project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dnd.demo.domain.project.entity.Project;
import com.dnd.demo.domain.project.enums.ProjectStatus;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
	Optional<Project> findByMemberIdAndProjectStatus(String memberId, ProjectStatus projectStatus);

	Optional<Project> findByMemberId(String memberId);

	Optional<Project> findByProjectIdAndMemberIdAndProjectStatus(Long projectId, String memberId, ProjectStatus status);
}

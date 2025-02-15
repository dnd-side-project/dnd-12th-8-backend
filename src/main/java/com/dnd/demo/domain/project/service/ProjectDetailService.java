package com.dnd.demo.domain.project.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dnd.demo.domain.project.dto.request.ProjectDetailRequest;
import com.dnd.demo.domain.project.entity.Project;
import com.dnd.demo.domain.project.entity.ProjectDetail;
import com.dnd.demo.domain.project.repository.ProjectDetailRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectDetailService {

	private final ProjectDetailRepository projectDetailRepository;

	@Transactional
	public void saveByProjectId(Project project, List<ProjectDetailRequest> projectDetailRequests) {
		List<ProjectDetail> projectDetails = Optional.ofNullable(projectDetailRequests)
			.orElseGet(Collections::emptyList)
			.stream()
			.map(request -> request.toEntity(project.getId()))
			.toList();

		projectDetailRepository.saveAll(projectDetails);
	}

	public void deleteByProjectId(Project project) {
		projectDetailRepository.deleteByProjectId(project.getId());
	}
}

package com.dnd.demo.domain.project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dnd.demo.domain.Quiz.entity.Quiz;
import com.dnd.demo.domain.Quiz.service.QuizOptionService;
import com.dnd.demo.domain.Quiz.service.QuizService;
import com.dnd.demo.domain.feedback.service.FeedbackFormService;
import com.dnd.demo.domain.project.dto.request.ProjectCreateRequest;
import com.dnd.demo.domain.project.dto.request.ProjectSaveRequest;
import com.dnd.demo.domain.project.dto.request.TemporaryProjectCreateRequest;
import com.dnd.demo.domain.project.dto.response.ProjectListResponseDto;
import com.dnd.demo.domain.project.entity.Project;
import com.dnd.demo.domain.project.enums.Job;
import com.dnd.demo.domain.project.enums.ProjectStatus;
import com.dnd.demo.domain.project.repository.ProjectQueryDslRepository;
import com.dnd.demo.domain.project.repository.ProjectRepository;
import com.dnd.demo.global.exception.CustomException;
import com.dnd.demo.global.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectService {

	private final ProjectRepository projectRepository;
	private final ProjectCategoryService projectCategoryService;
	private final ProjectDetailService projectDetailService;
	private final QuizService quizService;
	private final QuizOptionService quizOptionService;
	private final FeedbackFormService feedbackFormService;
	private final ProjectQueryDslRepository projectQueryDslRepository;

	@Transactional
	public Long saveTemporaryProject(String memberId, TemporaryProjectCreateRequest request) {
		Optional<Project> existingProject = projectRepository.findByMemberId(memberId);
		validateProjectFinalUpload(existingProject);

		Project project = existingProject
			.map(existing -> {
				existing.updateFromRequest(request);
				return existing;
			})
			.orElseGet(() -> request.toEntity(memberId));

		if (existingProject.isPresent()) {
			clearProjectData(project);
		}

		saveProjectData(request, project);
		return project.getProjectId();
	}

	@Transactional
	public Long createFinalProject(String memberId, ProjectCreateRequest request) {
		Optional<Project> existingProject = projectRepository.findByMemberId(memberId);
		validateProjectFinalUpload(existingProject);

		Project project = existingProject
			.filter(proj -> proj.getProjectStatus() == ProjectStatus.TEMPORARY)
			.map(existing -> {
				existing.updateFromRequest(request);
				existing.changeStatusToOpen();
				return existing;
			})
			.orElseGet(() -> request.toEntity(memberId));

		if (existingProject.isPresent()) {
			clearProjectData(project);
		}

		saveProjectData(request, project);
		return project.getProjectId();
	}

	public Page<ProjectListResponseDto> getPopularProjects(Pageable pageable) {
		Page<Project> projects = projectQueryDslRepository.findPopularProjects(pageable);
		return projects.map(ProjectListResponseDto::from);
	}

	public Page<ProjectListResponseDto> getRecommendedProjects(String memberId, Pageable pageable) {
		Page<Project> projects = projectQueryDslRepository.findRecommendedProjects(memberId, pageable);
		return projects.map(ProjectListResponseDto::from);
	}

	public Page<ProjectListResponseDto> searchProjects(String query, Job job, List<Long> categoryIds, Pageable pageable) {
		Page<Project> projects = projectQueryDslRepository.searchProjects(query, job, categoryIds, pageable);
		return projects.map(ProjectListResponseDto::from);
	}

	private void validateProjectFinalUpload(Optional<Project> existingProject) {
		if (existingProject.isPresent() && existingProject.get().getProjectStatus() == ProjectStatus.OPEN) {
			throw new CustomException(ErrorCode.PROJECT_FINAL_CREATE_ALREADY_UPLOAD);
		}
	}

	@Transactional
	public void clearProjectData(Project project) {
		projectCategoryService.deleteByProjectId(project);
		projectDetailService.deleteByProjectId(project);
		List<Quiz> deletedQuizzes = quizService.deleteByProjectId(project);
		quizOptionService.deleteByQuizIds(deletedQuizzes);
		feedbackFormService.deleteByProjectId(project);
	}

	private void saveProjectData(ProjectSaveRequest request, Project project) {
		projectRepository.save(project);
		projectCategoryService.saveByProjectId(project, request.platformCategories());
		projectDetailService.saveByProjectId(project, request.projectDetailRequests());
		List<Quiz> quizzes = quizService.save(project, request.quizRequests());
		quizOptionService.save(quizzes, request.quizRequests());
		feedbackFormService.save(project, request.feedbackFormRequests());
	}
}

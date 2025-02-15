package com.dnd.demo.domain.project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dnd.demo.domain.Quiz.entity.Quiz;
import com.dnd.demo.domain.Quiz.service.QuizOptionService;
import com.dnd.demo.domain.Quiz.service.QuizService;
import com.dnd.demo.domain.feedback.service.FeedbackFormService;
import com.dnd.demo.domain.project.dto.request.ProjectCreateRequest;
import com.dnd.demo.domain.project.entity.Project;
import com.dnd.demo.domain.project.enums.ProjectStatus;
import com.dnd.demo.domain.project.repository.ProjectRepository;

import jakarta.validation.Valid;
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

	@Transactional
	public Long createProject(String memberId, ProjectCreateRequest request) {
		Optional<Project> existingProject = projectRepository.findByMemberIdAndProjectStatus(memberId, ProjectStatus.TEMPORARY);

		boolean isNewProject = existingProject.isEmpty();
		Project project = existingProject
			.map(existing -> {
				existing.updateFromRequest(request);
				if(!request.isDraft()){
					existing.changeStatusToOpen();
				}
				return existing;
			})
			.orElseGet(() -> request.toEntity(memberId));

		if (!isNewProject) {
			clearProjectData(project);
		}

		saveProjectData(request, project);
		return project.getId();
	}

	@Transactional
	public void clearProjectData(Project project) {
		projectCategoryService.deleteByProjectId(project);
		projectDetailService.deleteByProjectId(project);

		List<Quiz> deletedQuizzes = quizService.deleteByProjectId(project);
		quizOptionService.deleteByQuizIds(deletedQuizzes);

		feedbackFormService.deleteByProjectId(project);
	}

	private void saveProjectData(ProjectCreateRequest request, Project project) {
		projectRepository.save(project);

		projectCategoryService.saveByProjectId(project, request.platformCategories());
		projectDetailService.saveByProjectId(project, request.projectDetailRequests());

		List<Quiz> quizzes = quizService.save(project, request.quizRequests());
		quizOptionService.save(quizzes, request.quizRequests());

		feedbackFormService.save(project, request.feedbackFormRequests());
	}
}

package com.dnd.demo.domain.project.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dnd.demo.domain.Quiz.entity.Quiz;
import com.dnd.demo.domain.Quiz.service.QuizOptionService;
import com.dnd.demo.domain.Quiz.service.QuizService;
import com.dnd.demo.domain.feedback.service.FeedbackFormService;
import com.dnd.demo.domain.project.dto.request.ProjectCreateRequest;
import com.dnd.demo.domain.project.entity.Project;
import com.dnd.demo.domain.project.repository.ProjectRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectService {

	private final QuizService quizService;
	private final FeedbackFormService feedbackFormService;
	private final QuizOptionService quizOptionService;
	private final ProjectRepository projectRepository;

	@Transactional
	public Long createProject(String memberId, ProjectCreateRequest request) {
		Project project = request.toEntity(memberId);
		projectRepository.save(project);

		Quiz quiz = quizService.save(project.getId(), request.quizRequest());
		quizOptionService.save(quiz.getId(), request.quizRequest().options());

		feedbackFormService.save(project.getId(), request.feedbackFormRequests());
		return project.getId();
	}
}

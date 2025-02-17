package com.dnd.demo.domain.Quiz.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dnd.demo.domain.Quiz.entity.Quiz;
import com.dnd.demo.domain.Quiz.entity.QuizOption;
import com.dnd.demo.domain.Quiz.repository.QuizRepository;
import com.dnd.demo.domain.Quiz.dto.request.QuizRequest;
import com.dnd.demo.domain.Quiz.dto.response.QuizResponse;
import com.dnd.demo.domain.project.entity.Project;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuizService {

	private final QuizRepository quizRepository;
	private final QuizOptionService quizOptionService;

	@Transactional
	public List<Quiz> save(Project project, List<QuizRequest> requests) {
		List<Quiz> quizzes = Optional.ofNullable(requests)
			.orElseGet(Collections::emptyList)
			.stream()
			.map(request -> request.toEntity(project.getProjectId()))
			.toList();

		return quizRepository.saveAll(quizzes);
	}

	public List<Quiz> deleteByProjectId(Project project) {
		return quizRepository.deleteByProjectId(project.getProjectId());
	}

	@Transactional(readOnly = true)
	public List<QuizResponse> getQuizzesWithOptionsByProjectId(Long projectId) {
		return Optional.ofNullable(quizRepository.findByProjectId(projectId))
			.orElse(Collections.emptyList())
			.stream()
			.map(quiz -> {
				List<QuizOption> quizOptions = quizOptionService.getQuizOptionsByQuizId(quiz.getQuizId());
				return QuizResponse.from(quiz, quizOptions);
			})
			.toList();
	}

}

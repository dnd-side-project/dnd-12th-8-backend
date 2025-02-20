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
import com.dnd.demo.domain.member.entity.Member;
import com.dnd.demo.domain.member.service.MemberService;
import com.dnd.demo.domain.project.entity.Project;
import com.dnd.demo.domain.project.enums.ProjectStatus;
import com.dnd.demo.domain.project.repository.ProjectRepository;
import com.dnd.demo.global.exception.CustomException;
import com.dnd.demo.global.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuizService {

	private final QuizRepository quizRepository;
	private final MemberService memberService;
	private final QuizOptionService quizOptionService;
	private final QuizCompletionService quizCompletionService;
	private final ProjectRepository projectRepository;

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
		return quizRepository.findByProjectId(projectId)
			.stream()
			.map(quiz -> {
				List<QuizOption> quizOptions = quizOptionService.getQuizOptionsByQuizId(quiz.getQuizId());
				return QuizResponse.from(quiz, quizOptions);
			})
			.toList();
	}

	@Transactional
	public int completeProjectQuiz(String memberId, Long projectId) {
		validateProject(projectId);
		validateQuizCompletionNotDuplicated(memberId, projectId);

		// Quiz quiz = getQuizByProjectId(projectId);
		// validateQuizProjectAssociation(projectId, quiz);

		Member member = memberService.rewardForQuizCompletion(memberId);
		quizCompletionService.save(memberId, projectId);

		return member.getPoints();
	}

	private void validateQuizCompletionNotDuplicated(String memberId, Long projectId) {
		if (quizCompletionService.hasMemberCompletedQuiz(memberId, projectId)) {
			throw new CustomException(ErrorCode.QUIZ_ALREADY_COMPLETED);
		}
	}

	private static void validateQuizProjectAssociation(Long projectId, Quiz quiz) {
		if (!quiz.getProjectId().equals(projectId)) {
			throw new CustomException(ErrorCode.INVALID_QUIZ_PROJECT);
		}
	}

	// @Transactional(readOnly = true)
	// public Quiz getQuizByProjectId(Long projectId) {
	// 	return quizRepository.findByProjectId(projectId)
	// 		.orElseThrow(() -> new CustomException(ErrorCode.QUIZ_NOT_FOUND));
	// }

	private void validateProject(Long projectId) {
		Project project = projectRepository.findById(projectId)
			.orElseThrow(() -> new CustomException(ErrorCode.PROJECT_NOT_FOUND));

		if (project.getProjectStatus() != ProjectStatus.OPEN) {
			throw new CustomException(ErrorCode.PROJECT_NOT_OPEN);
		}
	}

}

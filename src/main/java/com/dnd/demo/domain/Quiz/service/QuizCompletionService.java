package com.dnd.demo.domain.Quiz.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dnd.demo.domain.Quiz.entity.QuizCompletion;
import com.dnd.demo.domain.Quiz.repository.QuizCompletionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuizCompletionService {

	private final QuizCompletionRepository quizCompletionRepository;

	@Transactional
	public void save(String memberId, Long projectId) {
		quizCompletionRepository.save(new QuizCompletion(memberId, projectId));
	}

	@Transactional(readOnly = true)
	public boolean hasMemberCompletedQuiz(String memberId, Long projectId) {
		return quizCompletionRepository.existsByMemberIdAndProjectId(memberId, projectId);
	}
}

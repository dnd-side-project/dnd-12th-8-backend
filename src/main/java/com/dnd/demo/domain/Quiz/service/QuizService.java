package com.dnd.demo.domain.Quiz.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dnd.demo.domain.Quiz.entity.Quiz;
import com.dnd.demo.domain.Quiz.repository.QuizRepository;
import com.dnd.demo.domain.Quiz.dto.request.QuizRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuizService {

	private final QuizRepository quizRepository;

	@Transactional
	public Quiz save(Long projectId, QuizRequest request) {
		Quiz quiz = request.toEntity(projectId);
		return quizRepository.save(quiz);
	}
}

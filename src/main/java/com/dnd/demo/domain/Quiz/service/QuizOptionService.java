package com.dnd.demo.domain.Quiz.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dnd.demo.domain.Quiz.entity.QuizOption;
import com.dnd.demo.domain.Quiz.entity.QuizOptionKey;
import com.dnd.demo.domain.Quiz.repository.QuizOptionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuizOptionService {

	private final QuizOptionRepository quizOptionRepository;

	@Transactional
	public void save(Long quizId, List<String> options) {
		List<QuizOption> quizOptions = options.stream()
			.map(optionText -> QuizOption.builder()
				.quizId(quizId)
				.text(optionText)
				.build())
			.toList();

		quizOptionRepository.saveAll(quizOptions);
	}
}

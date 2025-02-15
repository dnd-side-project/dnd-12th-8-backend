package com.dnd.demo.domain.Quiz.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dnd.demo.domain.Quiz.dto.request.QuizRequest;
import com.dnd.demo.domain.Quiz.entity.Quiz;
import com.dnd.demo.domain.Quiz.entity.QuizOption;
import com.dnd.demo.domain.Quiz.repository.QuizOptionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuizOptionService {

	private final QuizOptionRepository quizOptionRepository;

	@Transactional
	public void save(List<Quiz> quizzes, List<QuizRequest> quizRequests) {
		List<QuizOption> quizOptions = IntStream.range(0, quizzes.size())
			.mapToObj(i -> Optional.ofNullable(quizRequests)
				.orElseGet(Collections::emptyList)
				.get(i)
				.toQuizOptions(quizzes.get(i).getId()))
			.flatMap(List::stream)
			.toList();

		quizOptionRepository.saveAll(quizOptions);
	}

	public void deleteByQuizIds(List<Quiz> quizzes) {
		List<Long> quizIds = quizzes.stream().map(Quiz::getId).toList();
		quizOptionRepository.deleteByQuizIdIn(quizIds);
	}
}

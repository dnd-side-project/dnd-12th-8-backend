package com.dnd.demo.domain.Quiz.dto.response;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.dnd.demo.domain.Quiz.entity.Quiz;
import com.dnd.demo.domain.Quiz.entity.QuizOption;

public record QuizResponse(
	String question,
	List<String> options,
	String answer
) {
	public static QuizResponse from(Quiz quiz, List<QuizOption> quizOptions) {
		List<String> options = Optional.ofNullable(quizOptions)
			.orElse(Collections.emptyList())
			.stream()
			.map(QuizOption::getText)
			.toList();

		return new QuizResponse(quiz.getQuestion(), options, quiz.getAnswer());
	}

}

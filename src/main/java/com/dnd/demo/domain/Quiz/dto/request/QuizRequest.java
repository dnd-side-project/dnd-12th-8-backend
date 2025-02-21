package com.dnd.demo.domain.Quiz.dto.request;

import java.util.List;

import com.dnd.demo.domain.Quiz.entity.Quiz;
import com.dnd.demo.domain.Quiz.entity.QuizOption;

import jakarta.validation.constraints.*;

public record QuizRequest(
	String question,

	@Size(max = 4, message = "객관식 보기는 최대 4개여야 합니다.")
	List<String> options,

	String answer
) {
	public Quiz toEntity(Long projectId) {
		return Quiz.builder()
			.projectId(projectId)
			.question(question)
			.answer(answer)
			.build();
	}

	public List<QuizOption> toQuizOptions(Long quizId) {
		return options.stream()
			.map(optionText -> QuizOption.builder()
				.quizId(quizId)
				.text(optionText)
				.build())
			.toList();
	}
}

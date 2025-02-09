package com.dnd.demo.domain.Quiz.dto.request;

import java.util.List;

import com.dnd.demo.domain.Quiz.entity.Quiz;

public record QuizRequest(
	String question,
	List<String> options
) {
	public Quiz toEntity(Long projectId) {
		return Quiz.builder()
			.projectId(projectId)
			.question(question)
			.build();
	}
}

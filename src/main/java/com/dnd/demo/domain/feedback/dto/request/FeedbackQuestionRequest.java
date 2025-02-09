package com.dnd.demo.domain.feedback.dto.request;

import java.util.List;

import com.dnd.demo.domain.feedback.entity.FeedbackQuestion;
import com.dnd.demo.domain.project.enums.QuestionType;

public record FeedbackQuestionRequest(
	String questionText,
	QuestionType questionType,
	List<String> options,
	String abImageAUrl,
	String abImageBUrl
) {
	public FeedbackQuestion toEntity() {
		return FeedbackQuestion.builder()
			.questionText(questionText)
			.questionType(questionType)
			.options(options != null ? options : List.of()) // Null 방지
			.abImageAUrl(abImageAUrl)
			.abImageBUrl(abImageBUrl)
			.build();
	}
}

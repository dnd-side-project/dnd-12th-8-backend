package com.dnd.demo.domain.feedback.dto.response;

import java.util.List;

import com.dnd.demo.domain.feedback.entity.FeedbackForm;
import com.dnd.demo.domain.feedback.entity.FeedbackQuestion;
import com.dnd.demo.domain.project.enums.QuestionType;

public record FeedbackFormResponse(
	String question,
	QuestionType type,
	List<String> options,
	String abImageAUrl,
	String abImageBUrl,
	boolean isRequired,
	Integer timeLimit
) {
	public static FeedbackFormResponse from(FeedbackQuestion feedbackQuestion) {
		return new FeedbackFormResponse(
			feedbackQuestion.getQuestion(),
			feedbackQuestion.getType(),
			feedbackQuestion.getOptions() != null ? feedbackQuestion.getOptions() : List.of(),
			feedbackQuestion.getAbImageAUrl(),
			feedbackQuestion.getAbImageBUrl(),
			feedbackQuestion.isRequired(),
			feedbackQuestion.getTimeLimit()
		);
	}

	public static List<FeedbackFormResponse> from(FeedbackForm feedbackForm) {
		if (feedbackForm.getQuestions() == null || feedbackForm.getQuestions().isEmpty()) {
			return List.of();
		}

		return feedbackForm.getQuestions().stream()
			.map(FeedbackFormResponse::from)
			.toList();
	}
}

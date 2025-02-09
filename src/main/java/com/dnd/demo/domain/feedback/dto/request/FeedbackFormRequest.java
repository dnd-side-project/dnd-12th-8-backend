package com.dnd.demo.domain.feedback.dto.request;

import java.util.List;

import com.dnd.demo.domain.feedback.entity.FeedbackForm;

public record FeedbackFormRequest(
	List<FeedbackQuestionRequest> feedbackQuestions
) {
	public FeedbackForm toEntity(Long projectId) {
		return FeedbackForm.builder()
			.projectId(projectId)
			.questions((feedbackQuestions != null ? feedbackQuestions : List.<FeedbackQuestionRequest>of())
				.stream()
				.map(FeedbackQuestionRequest::toEntity)
				.toList())
			.build();
	}
}

package com.dnd.demo.domain.feedback.dto.response;

import java.util.List;

import com.dnd.demo.domain.feedback.entity.feedbackresult.FeedbackQuestionResult;
import com.dnd.demo.domain.feedback.entity.feedbackresult.FeedbackQuestionResultType;
import com.dnd.demo.domain.project.enums.QuestionType;

import lombok.Builder;

@Builder
public record FeedbackQuestionResultResponse(
	String questionId,
	String questionText,  //  질문 내용 추가
	QuestionType questionType,
	Integer questionResponseCount,
	Integer totalPoints,
	Integer aResponseCount,
	Integer bResponseCount,
	List<FeedbackQuestionResultType> responseResultList
) {
	public static FeedbackQuestionResultResponse fromEntity(FeedbackQuestionResult questionResult, String questionText) {
		return FeedbackQuestionResultResponse.builder()
			.questionId(questionResult.getQuestionId())
			.questionText(questionText)  //  질문 내용 추가
			.questionType(questionResult.getQuestionType())
			.questionResponseCount(questionResult.getQuestionResponseCount())
			.totalPoints(questionResult.getTotalPoints())
			.aResponseCount(questionResult.getAResponseCount())
			.bResponseCount(questionResult.getBResponseCount())
			.responseResultList(questionResult.getResponseResultList())
			.build();
	}
}

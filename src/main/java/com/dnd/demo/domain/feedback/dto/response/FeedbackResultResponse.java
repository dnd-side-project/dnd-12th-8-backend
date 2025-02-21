package com.dnd.demo.domain.feedback.dto.response;

import com.dnd.demo.domain.feedback.entity.FeedbackResult;
import com.dnd.demo.domain.feedback.entity.feedbackresult.FeedbackQuestionResult;

import java.util.List;
import java.util.Map;

import lombok.Builder;

// @Builder
// public record FeedbackResultResponse(Long projectId,
// 									 Integer totalResponseCount,
// 									 List<FeedbackQuestionResult> feedbackQuestionResult) {
//
// 	public static FeedbackResultResponse fromEntity(FeedbackResult feedbackResult) {
// 		return FeedbackResultResponse.builder()
// 			.projectId(feedbackResult.getProjectId())
// 			.totalResponseCount(feedbackResult.getTotalResponseCount())
// 			.feedbackQuestionResult(feedbackResult.getFeedbackQuestionResult())
// 			.build();
// 	}
//
// }

@Builder
public record FeedbackResultResponse(
    Long projectId,
    Integer totalResponseCount,
    List<FeedbackQuestionResultResponse> feedbackQuestionResults // 기존 FeedbackQuestionResult → FeedbackQuestionResultResponse 변경
) {
    public static FeedbackResultResponse fromEntity(FeedbackResult feedbackResult, Map<String, String> questionTextMap) {
        List<FeedbackQuestionResultResponse> questionResults = feedbackResult.getFeedbackQuestionResult().stream()
            .map(result -> FeedbackQuestionResultResponse.fromEntity(
                result,
                questionTextMap.getOrDefault(result.getQuestionId(), "알 수 없는 질문") //  질문 내용 추가
            ))
            .toList();

        return FeedbackResultResponse.builder()
            .projectId(feedbackResult.getProjectId())
            .totalResponseCount(feedbackResult.getTotalResponseCount())
            .feedbackQuestionResults(questionResults) // 질문 내용 포함된 리스트 사용
            .build();
    }
}

package com.dnd.demo.domain.feedback.dto.response;

import com.dnd.demo.domain.feedback.entity.FeedbackResult;
import com.dnd.demo.domain.feedback.entity.feedbackresult.FeedbackQuestionResult;
import java.util.List;
import lombok.Builder;

@Builder
public record FeedbackResultResponse(Long projectId,
                                     Integer totalResponseCount,
                                     List<FeedbackQuestionResult> feedbackQuestionResult) {

    public static FeedbackResultResponse fromEntity(FeedbackResult feedbackResult) {
        return FeedbackResultResponse.builder().projectId(feedbackResult.getProjectId())
          .totalResponseCount(feedbackResult.getTotalResponseCount())
          .feedbackQuestionResult(feedbackResult.getFeedbackQuestionResult())
          .build();
    }

}

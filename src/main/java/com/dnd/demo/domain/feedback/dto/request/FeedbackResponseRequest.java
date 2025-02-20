package com.dnd.demo.domain.feedback.dto.request;

import com.dnd.demo.domain.feedback.entity.FeedbackResponse;
import java.util.List;

public record FeedbackResponseRequest(Long projectId,
                                      List<FeedbackAnswerRequest> answers) {

    public FeedbackResponse toEntity(String memberId, String formId) {
        return FeedbackResponse.builder().formId(formId)
          .memberId(memberId)
          .answers(answers.stream().map(FeedbackAnswerRequest::toEntity).toList()).build();
    }
}

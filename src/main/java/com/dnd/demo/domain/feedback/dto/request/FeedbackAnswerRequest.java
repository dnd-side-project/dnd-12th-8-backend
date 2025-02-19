package com.dnd.demo.domain.feedback.dto.request;

import com.dnd.demo.domain.feedback.entity.FeedbackAnswer;
import lombok.Builder;

@Builder
public record FeedbackAnswerRequest(String questionId,
                                    String responseText) {

    public FeedbackAnswer toEntity() {
        return FeedbackAnswer.builder()
          .questionId(questionId)
          .responseText(responseText)
          .build();
    }
}

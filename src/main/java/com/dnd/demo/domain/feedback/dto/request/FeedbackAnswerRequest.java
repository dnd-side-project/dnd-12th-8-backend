package com.dnd.demo.domain.feedback.dto.request;

import com.dnd.demo.domain.feedback.entity.FeedbackAnswer;
import com.dnd.demo.domain.project.enums.QuestionType;
import lombok.Builder;

@Builder
public record FeedbackAnswerRequest(String questionId
  , QuestionType questionType
  , String selectedOption
  , String responseText
) {

    public FeedbackAnswer toEntity() {
        return FeedbackAnswer.builder()
          .questionId(questionId)
          .questionType(questionType)
          .selectedOption(selectedOption)
          .responseText(responseText)
          .build();
    }
}

package com.dnd.demo.domain.feedback.entity.feedbackresult;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FeedbackQuestionResultType {

    String questionText; // 항목명
    Integer responseCount; // 인원수
    String answerText; // 답변명 (A/B랑 주관식)
    Integer point;
}

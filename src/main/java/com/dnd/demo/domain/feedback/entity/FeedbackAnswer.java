package com.dnd.demo.domain.feedback.entity;

import com.dnd.demo.domain.project.enums.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeedbackAnswer {

    private String questionId;
    private QuestionType questionType;
    private String responseText; // 객관식 주관식 or A/B 테스트 응답
}


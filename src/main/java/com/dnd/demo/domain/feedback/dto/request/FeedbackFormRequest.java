package com.dnd.demo.domain.feedback.dto.request;

import com.dnd.demo.domain.feedback.entity.FeedbackQuestion;
import com.dnd.demo.domain.project.enums.QuestionType;
import jakarta.validation.constraints.Pattern;
import java.util.List;
import java.util.UUID;

public record FeedbackFormRequest(

  String question,
  QuestionType type,
  List<String> options,

  @Pattern(regexp = "^(http|https)://.*$", message = "A/B 테스트 이미지 A는 유효한 URL 형식이어야 합니다.")
  String abImageAUrl,

  @Pattern(regexp = "^(http|https)://.*$", message = "A/B 테스트 이미지 B는 유효한 URL 형식이어야 합니다.")
  String abImageBUrl,

  boolean isRequired,
  Integer timeLimit
) {

    public FeedbackQuestion toEntity() {
        return FeedbackQuestion.builder()
          .questionId(UUID.randomUUID().toString().substring(0, 8))
          .question(question)
          .type(type)
          .options(options != null ? options : List.of())
          .abImageAUrl(abImageAUrl)
          .abImageBUrl(abImageBUrl)
          .isRequired(isRequired)
          .timeLimit(timeLimit)
          .build();
    }
}

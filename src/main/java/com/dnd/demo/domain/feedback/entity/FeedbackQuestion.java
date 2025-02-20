package com.dnd.demo.domain.feedback.entity;

import com.dnd.demo.domain.project.enums.QuestionType;
import jakarta.persistence.Id;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeedbackQuestion {

    @Id
    private String questionId;
    private String question;
    private QuestionType type;
    private List<String> options;
    private String abImageAUrl;
    private String abImageBUrl;
    private boolean isRequired;
    private Integer timeLimit;
}

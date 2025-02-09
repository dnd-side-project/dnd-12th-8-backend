package com.dnd.demo.domain.Quiz.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class QuizOptionKey {

    private Long optionId;   // 복합키 자동 증가 에러 발생하여 대체
    private Long quizId;
}

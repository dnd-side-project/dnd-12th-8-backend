package com.dnd.demo.domain.Quiz.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
public class QuizOptionKey {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long optionId;
    private Long quizId;

}

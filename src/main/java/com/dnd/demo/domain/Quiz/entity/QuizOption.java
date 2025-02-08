package com.dnd.demo.domain.Quiz.entity;


import com.dnd.demo.common.entity.BaseEntity;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

@Entity
public class QuizOption extends BaseEntity {

    @EmbeddedId
    private QuizOptionKey quizOptionKey;

    private String text;
}

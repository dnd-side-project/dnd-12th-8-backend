package com.dnd.demo.domain.Quiz.entity;

import com.dnd.demo.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class QuizResult extends BaseEntity {

    @Id
    private Long projectId;

    private Integer participantCount;
    private Integer passCount;
}

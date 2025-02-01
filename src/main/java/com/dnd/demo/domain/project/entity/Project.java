package com.dnd.demo.domain.project.entity;

import com.dnd.demo.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Project extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectId;
    private String memberId;
    private String title;
    private String description;
    private String startDate;
    private String dueDate;
    private String category;
    private String targetJob;
    private String targetLevel;
    private String logoImgUrl;
    private String thumbnailImgUrl;
    private String status;
    private Integer participantCount;
    private Integer favoriteCount;
}

package com.dnd.demo.domain.project.entity;

import com.dnd.demo.global.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

    @Enumerated(EnumType.STRING)
    private Category category;
    @Enumerated(EnumType.STRING)
    private Job targetJob;
    @Enumerated(EnumType.STRING)
    private Level targetLevel;
    private String logoImgUrl;
    private String thumbnailImgUrl;
    private String status;
    private Integer participantCount;
    private Integer favoriteCount;
    private String projectMembers;
}

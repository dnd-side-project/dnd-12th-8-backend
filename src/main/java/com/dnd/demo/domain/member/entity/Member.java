package com.dnd.demo.domain.member.entity;

import com.dnd.demo.common.entity.BaseEntity;
import com.dnd.demo.domain.project.entity.Category;
import com.dnd.demo.domain.project.entity.Job;
import com.dnd.demo.domain.project.entity.Level;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;

@Entity
public class Member extends BaseEntity {

    @Id
    private Long memberId;

    @Enumerated(EnumType.STRING)
    private Job job;
    @Enumerated(EnumType.STRING)
    private Level level;
    @Enumerated(EnumType.STRING)
    private Category category;
    private String email;
    private Integer points;
    private String memberName;
    private String profileUrl = null;
}

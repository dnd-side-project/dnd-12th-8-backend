package com.dnd.demo.domain.project.entity;

import com.dnd.demo.domain.project.enums.DetailType;
import com.dnd.demo.global.common.entity.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ProjectDetail extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectDetailId;

    private Long projectId;

    @Enumerated(EnumType.STRING)
    private DetailType type;

    private String detailContent;
}

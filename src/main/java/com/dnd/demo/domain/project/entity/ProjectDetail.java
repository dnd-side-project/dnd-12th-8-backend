package com.dnd.demo.domain.project.entity;

import com.dnd.demo.common.entity.BaseEntity;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;


@Entity
public class ProjectDetail extends BaseEntity {

    @EmbeddedId
    private ProjectDetailKey projectDetailKey;

    private String detailType;
    private String detailUrl;
}

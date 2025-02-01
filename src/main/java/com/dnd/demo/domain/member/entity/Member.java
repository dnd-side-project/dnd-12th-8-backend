package com.dnd.demo.domain.member.entity;

import com.dnd.demo.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Member extends BaseEntity {

    @Id
    private Long memberId;

    private String job;
    private String level;
    private String email;
    private Integer points;
    private String memberName;
    private String profileUrl = null;
}

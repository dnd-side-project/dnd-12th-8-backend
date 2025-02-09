package com.dnd.demo.domain.project.entity;

import com.dnd.demo.domain.project.enums.Job;
import com.dnd.demo.domain.project.enums.Level;
import com.dnd.demo.domain.project.enums.Status;
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
public class Project extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String memberId;
	private String title;
	private String description;
	private String startDate;
	private String dueDate;

	// @Enumerated(EnumType.STRING)
	// private Category category;

	@Enumerated(EnumType.STRING)
	private Job targetJob;
	@Enumerated(EnumType.STRING)
	private Level targetLevel;
	private String logoImgUrl;
	private String thumbnailImgUrl;
	private Status status;
	private Integer participantCount;
	private Integer favoriteCount;
	private String projectMembers;
}

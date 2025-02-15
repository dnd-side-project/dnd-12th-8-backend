package com.dnd.demo.domain.project.entity;

import java.util.Optional;

import com.dnd.demo.domain.project.dto.request.ProjectCreateRequest;
import com.dnd.demo.domain.project.enums.Job;
import com.dnd.demo.domain.project.enums.Level;
import com.dnd.demo.domain.project.enums.ProjectStatus;
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
	private String logoImgUrl;
	private String title;
	private String description;
	private String dueDate;
	@Enumerated(EnumType.STRING)
	private Job targetJob;
	@Enumerated(EnumType.STRING)
	private Level targetLevel;
	private Boolean isAdvertised;
	private String projectMemberEmails;
	private String thumbnailImgUrl;
	private ProjectStatus projectStatus;
	private Integer participantCount;
	private Integer favoriteCount;

	public void updateFromRequest(ProjectCreateRequest request) {
		this.logoImgUrl = Optional.ofNullable(request.logoImgUrl()).orElse(this.logoImgUrl);
		this.title = Optional.ofNullable(request.title()).orElse(this.title);
		this.description = Optional.ofNullable(request.description()).orElse(this.description);
		this.dueDate = Optional.ofNullable(request.dueDate()).orElse(this.dueDate);
		this.targetJob = Optional.ofNullable(request.targetJob()).orElse(this.targetJob);
		this.targetLevel = Optional.ofNullable(request.targetLevel()).orElse(this.targetLevel);
		this.isAdvertised = Optional.ofNullable(request.isAdvertised()).orElse(this.isAdvertised);
		this.projectMemberEmails = Optional.ofNullable(request.projectMemberEmails()).orElse(this.projectMemberEmails);
		this.thumbnailImgUrl = Optional.ofNullable(request.thumbnailImgUrl()).orElse(this.thumbnailImgUrl);
	}

	public void changeStatusToOpen() {
		this.projectStatus = ProjectStatus.OPEN;
	}
}

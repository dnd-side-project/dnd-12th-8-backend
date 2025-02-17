package com.dnd.demo.domain.project.dto.request;

import java.util.List;

import com.dnd.demo.domain.Quiz.dto.request.QuizRequest;
import com.dnd.demo.domain.feedback.dto.request.FeedbackFormRequest;
import com.dnd.demo.domain.project.entity.Project;
import com.dnd.demo.domain.project.enums.Job;
import com.dnd.demo.domain.project.enums.Level;
import com.dnd.demo.domain.project.enums.ProjectStatus;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ProjectCreateRequest(
	@NotNull(message = "임시 저장 여부를 명시해야 합니다.")
	Boolean isDraft,

	@Valid
	@NotNull(message = "카테고리는 필수 입력 항목입니다.")
	PlatformCategoryRequest platformCategories,

	@NotBlank(message = "로고 이미지는 필수 입력 항목입니다.")
	@Pattern(
		regexp = "^(https?:\\/\\/.*\\.(png|jpe?g|webp))$",
		message = "로고 이미지는 유효한 URL 형식이며, .png, .jpg, .jpeg, .webp 형식만 허용됩니다."
	)
	String logoImgUrl,

	@NotBlank(message = "프로젝트 제목은 필수 입력 항목입니다.")
	@Size(max = 100, message = "프로젝트 제목은 최대 100자까지 입력 가능합니다.")
	String title,

	@NotBlank(message = "프로젝트 설명은 필수 입력 항목입니다.")
	@Size(max = 1000, message = "프로젝트 설명은 최대 1000자까지 입력 가능합니다.")
	String description,

	@NotBlank(message = "마감일은 필수 입력 항목입니다.")
	String dueDate,

	@NotNull(message = "대상 직군은 필수 입력 항목입니다.")
	Job targetJob,

	@NotNull(message = "대상 숙련도는 필수 입력 항목입니다.")
	Level targetLevel,

	@NotNull(message = "광고 여부는 필수 입력 항목입니다.")
	Boolean isAdvertised,

	@NotBlank(message = "프로젝트 멤버 이메일 목록을 입력해야 합니다.")
	String projectMemberEmails,

	@NotBlank(message = "썸네일 이미지는 필수 입력 항목입니다.")
	@Pattern(
		regexp = "^(https?:\\/\\/.*\\.(png|jpe?g|webp))$",
		message = "썸네일 이미지는 유효한 URL 형식이며, .png, .jpg, .jpeg, .webp 형식만 허용됩니다."
	)
	String thumbnailImgUrl,

	@NotEmpty(message = "프로젝트 세부 정보는 최소 1개 이상 입력해야 합니다.")
	List<@Valid ProjectDetailRequest> projectDetailRequests,

	@NotEmpty(message = "사전 퀴즈는 최소 1개 이상 입력해야 합니다.")
	List<@Valid QuizRequest> quizRequests,

	@NotEmpty(message = "피드백 질문은 최소 1개 이상 입력해야 합니다.")
	List<@Valid FeedbackFormRequest> feedbackFormRequests

) implements ProjectSaveRequest {

	public Project toEntity(String memberId) {
		return Project.builder()
			.memberId(memberId)
			.logoImgUrl(logoImgUrl)
			.title(title)
			.description(description)
			.dueDate(dueDate)
			.targetJob(targetJob)
			.targetLevel(targetLevel)
			.isAdvertised(isAdvertised != null ? isAdvertised : false)
			.projectMemberEmails(projectMemberEmails)
			.thumbnailImgUrl(thumbnailImgUrl)
			.projectStatus(ProjectStatus.OPEN)
			.favoriteCount(0)
			.participantCount(0)
			.build();
	}

	private ProjectStatus determineProjectStatus() {
		return Boolean.TRUE.equals(isDraft) ? ProjectStatus.TEMPORARY : ProjectStatus.OPEN;
	}
}

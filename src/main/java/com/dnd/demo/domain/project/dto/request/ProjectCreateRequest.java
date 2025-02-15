package com.dnd.demo.domain.project.dto.request;

import java.util.List;

import com.dnd.demo.domain.Quiz.dto.request.QuizRequest;
import com.dnd.demo.domain.feedback.dto.request.FeedbackFormRequest;
import com.dnd.demo.domain.project.entity.Project;
import com.dnd.demo.domain.project.enums.Job;
import com.dnd.demo.domain.project.enums.Level;
import com.dnd.demo.domain.project.enums.ProjectStatus;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ProjectCreateRequest(
	@NotNull(message = "임시 저장 여부를 명시해야 합니다.")
	Boolean isDraft,

	PlatformCategoryRequest platformCategories,

	@Pattern(
		regexp = "^(https?:\\/\\/.*\\.(png|jpe?g|webp))$",
		message = "로고 이미지는 유효한 URL 형식이며, .png, .jpg, .jpeg, .webp 형식만 허용됩니다."
	)
	String logoImgUrl,

	@Size(max = 100, message = "프로젝트 제목은 최대 100자까지 입력 가능합니다.")
	String title,

	@Size(max = 1000, message = "프로젝트 설명은 최대 1000자까지 입력 가능합니다.")
	String description,

	String dueDate,
	Job targetJob,
	Level targetLevel,
	Boolean isAdvertised,
	String projectMemberEmails,

	@Pattern(
		regexp = "^(https?:\\/\\/.*\\.(png|jpe?g|webp))$",
		message = "썸네일 이미지는 유효한 URL 형식이며, .png, .jpg, .jpeg, .webp 형식만 허용됩니다."
	)
	String thumbnailImgUrl,

	List<@Valid ProjectDetailRequest> projectDetailRequests,
	List<@Valid QuizRequest> quizRequests,
	List<@Valid FeedbackFormRequest> feedbackFormRequests
) {
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
			.projectStatus(determineProjectStatus()) // ✅ 상태 자동 설정
			.favoriteCount(0)
			.participantCount(0)
			.build();
	}

	private ProjectStatus determineProjectStatus() {
		if (Boolean.TRUE.equals(isDraft)) {
			return ProjectStatus.TEMPORARY;
		}

		return ProjectStatus.OPEN;
	}
}

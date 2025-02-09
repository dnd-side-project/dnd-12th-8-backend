package com.dnd.demo.domain.project.dto.request;

import java.util.List;

import com.dnd.demo.domain.Quiz.dto.request.QuizRequest;
import com.dnd.demo.domain.feedback.dto.request.FeedbackFormRequest;
import com.dnd.demo.domain.project.entity.Project;
import com.dnd.demo.domain.project.enums.Job;
import com.dnd.demo.domain.project.enums.Level;


public record ProjectCreateRequest(
	String title,
	String description,
	String startDate,
	String dueDate,
	Job targetJob,
	Level targetLevel,
	String logoImgUrl,
	String thumbnailImgUrl,
	String projectMembers,
	List<Long> categoryIds,

	QuizRequest quizRequest,
	List<FeedbackFormRequest> feedbackFormRequests
) {
	public Project toEntity(String memberId) {
		return Project.builder()
			.memberId(memberId)
			.title(title)
			.description(description)
			.startDate(startDate)
			.dueDate(dueDate)
			.targetJob(targetJob)
			.targetLevel(targetLevel)
			.logoImgUrl(logoImgUrl)
			.thumbnailImgUrl(thumbnailImgUrl)
			.projectMembers(projectMembers)
			.favoriteCount(0)
			.build();
	}
}

package com.dnd.demo.domain.project.dto.request;

import java.util.List;

import com.dnd.demo.domain.Quiz.dto.request.QuizRequest;
import com.dnd.demo.domain.feedback.dto.request.FeedbackFormRequest;
import com.dnd.demo.domain.project.enums.Job;
import com.dnd.demo.domain.project.enums.Level;

public interface ProjectSaveRequest {
	PlatformCategoryRequest platformCategories();
	String logoImgUrl();
	String title();
	String description();
	String dueDate();
	Job targetJob();
	Level targetLevel();
	Boolean isAdvertised();
	String projectMemberEmails();
	String thumbnailImgUrl();
	List<ProjectDetailRequest> projectDetailRequests();
	List<QuizRequest> quizRequests();
	List<FeedbackFormRequest> feedbackFormRequests();
}

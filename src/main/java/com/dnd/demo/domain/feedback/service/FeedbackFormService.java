package com.dnd.demo.domain.feedback.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dnd.demo.domain.feedback.dto.request.FeedbackFormRequest;
import com.dnd.demo.domain.feedback.entity.FeedbackForm;
import com.dnd.demo.domain.feedback.entity.FeedbackQuestion;
import com.dnd.demo.domain.feedback.repository.FeedbackFormRepository;
import com.dnd.demo.domain.feedback.dto.response.FeedbackFormResponse;
import com.dnd.demo.domain.project.entity.Project;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FeedbackFormService {

	private final FeedbackFormRepository feedbackFormRepository;

	@Transactional
	public void save(Project project, List<FeedbackFormRequest> requests) {
		List<FeedbackQuestion> feedbackQuestions = Optional.ofNullable(requests)
			.orElseGet(Collections::emptyList)
			.stream()
			.map(FeedbackFormRequest::toEntity)
			.toList();

		FeedbackForm feedbackForm = FeedbackForm.builder()
			.projectId(project.getProjectId())
			.questions(feedbackQuestions)
			.build();

		feedbackFormRepository.save(feedbackForm);
	}

	public void deleteByProjectId(Project project) {
		feedbackFormRepository.deleteByProjectId(project.getProjectId());
	}

	@Transactional(readOnly = true)
	public List<FeedbackFormResponse> getFeedbackFormsByProjectId(Long projectId) {
		return feedbackFormRepository.findByProjectId(projectId)
			.stream()
			.flatMap(feedbackForm -> FeedbackFormResponse.from(feedbackForm).stream())
			.toList();
	}
}

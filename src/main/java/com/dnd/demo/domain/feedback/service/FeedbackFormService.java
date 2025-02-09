package com.dnd.demo.domain.feedback.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dnd.demo.domain.feedback.entity.FeedbackForm;
import com.dnd.demo.domain.feedback.repository.FeedbackFormRepository;
import com.dnd.demo.domain.feedback.dto.request.FeedbackFormRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FeedbackFormService {

	private final FeedbackFormRepository feedbackFormRepository;

	@Transactional
	public void save(Long projectId, List<FeedbackFormRequest> requests) {
		List<FeedbackForm> feedbackForms = requests.stream()
			.map(request -> request.toEntity(projectId))
			.toList();
		feedbackFormRepository.saveAll(feedbackForms);
	}
}

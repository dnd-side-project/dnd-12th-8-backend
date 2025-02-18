package com.dnd.demo.domain.feedback.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dnd.demo.domain.feedback.dto.response.FeedbackFormResponse;
import com.dnd.demo.domain.feedback.service.FeedbackFormService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "피드백 폼 API", description = "프로젝트 피드백 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/feedback_form")
public class FeedbackFormController {

	private final FeedbackFormService feedbackFormService;

	@Operation(summary = "프로젝트 피드백 폼 조회", description = "사용자가 '피드백 하기' 버튼을 눌렀을 때 해당 프로젝트의 피드백 폼 데이터를 조회합니다.")
	@GetMapping("/{projectId}")
	public ResponseEntity<List<FeedbackFormResponse>> getFeedbackForms(@PathVariable Long projectId) {
		List<FeedbackFormResponse> feedbackForms = feedbackFormService.getFeedbackFormsByProjectId(projectId);
		return ResponseEntity.ok(feedbackForms);
	}
}

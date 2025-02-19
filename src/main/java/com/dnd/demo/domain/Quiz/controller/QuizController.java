package com.dnd.demo.domain.Quiz.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dnd.demo.domain.Quiz.service.QuizService;
import com.dnd.demo.domain.member.dto.response.PointResponseDto;
import com.dnd.demo.global.auth.dto.OAuthUserDetails;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "사전 퀴즈 API", description = "퀴즈 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/quizzes")
public class QuizController {

	private final QuizService quizService;

	@Operation(summary = "프로젝트 사전 퀴즈 완료", description = "사전 퀴즈 5문제 전부 풀면 퀴즈 완료버튼을 통해 포인트를 지급합니다")
	@PostMapping("/{projectId}/completion")
	public ResponseEntity<PointResponseDto> completeQuiz(
		@PathVariable Long projectId,
		@AuthenticationPrincipal OAuthUserDetails userDetails) {

		int updatedPoints = quizService.completeProjectQuiz(userDetails.getMemberId(), projectId);
		return ResponseEntity.ok(new PointResponseDto(userDetails.getMemberId(), updatedPoints));
	}
}

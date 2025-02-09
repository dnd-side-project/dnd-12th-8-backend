package com.dnd.demo.domain.project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dnd.demo.common.dto.ApiResponse;
import com.dnd.demo.domain.project.dto.request.ProjectCreateRequest;
import com.dnd.demo.domain.project.service.ProjectService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "프로젝트 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/projects")
// @SecurityRequirement(name = "BearerAuth")
public class ProjectController {

	private final ProjectService projectService;

	@Operation(summary = "프로젝트 생성")
	@PostMapping("/{memberId}")
	public ResponseEntity<ApiResponse<Long>> createProject(@Parameter(description = "사용자 ID (로그인 후 인증 객체 오류로 인한 임시 추가 추후 인증 객체로 변경 예정)")
		@PathVariable String memberId,
		@RequestBody @Valid ProjectCreateRequest request) {
		Long projectId = projectService.createProject(memberId, request);
		return ResponseEntity.ok(new ApiResponse<>(HttpStatus.CREATED.value(), "생성 성공", projectId));
	}
}

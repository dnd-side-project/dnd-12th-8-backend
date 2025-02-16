package com.dnd.demo.domain.project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dnd.demo.common.dto.ApiResponse;
import com.dnd.demo.domain.project.dto.request.ProjectCreateRequest;
import com.dnd.demo.domain.project.dto.request.TemporaryProjectCreateRequest;
import com.dnd.demo.domain.project.service.ProjectService;
import com.dnd.demo.global.auth.dto.OAuthUserDetails;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "프로젝트 API", description = "프로젝트 생성 및 임시 저장 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/projects")
public class ProjectController {

	private final ProjectService projectService;

	@Operation(summary = "프로젝트 임시 저장", description = "임시 저장 API입니다. 필수 입력값이 없습니다.")
	@PostMapping("/temporary")
	public ResponseEntity<ApiResponse<Long>> saveTemporaryProject(
		@AuthenticationPrincipal OAuthUserDetails oAuthUserDetails,
		@Valid @RequestBody TemporaryProjectCreateRequest request) {
		Long projectId = projectService.saveTemporaryProject(oAuthUserDetails.getMemberId(), request);
		return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(HttpStatus.CREATED.value(), "임시 저장 성공", projectId));
	}

	@Operation(summary = "프로젝트 최종 업로드", description = "최종 프로젝트 업로드 API입니다. 모든 필드가 필수입니다.")
	@PostMapping("/final")
	public ResponseEntity<ApiResponse<Long>> saveFinalProject(
		@AuthenticationPrincipal OAuthUserDetails oAuthUserDetails,
		@Valid @RequestBody ProjectCreateRequest request) {
		Long projectId = projectService.createFinalProject(oAuthUserDetails.getMemberId(), request);
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(HttpStatus.OK.value(), "프로젝트 생성 성공", projectId));
	}
}

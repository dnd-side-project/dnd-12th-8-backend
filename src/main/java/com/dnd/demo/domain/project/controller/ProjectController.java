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

@Tag(name = "프로젝트 API", description = "프로젝트 생성 및 임시 저장 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/projects")
public class ProjectController {

	private final ProjectService projectService;

	@Operation(summary = "프로젝트 생성 및 임시 저장",
		description = "프로젝트를 임시 저장하거나 최종 생성하는 데 사용됩니다. " +
			"요청의 isDraft 값이 true이면 임시 저장, false이면 최종 프로젝트 생성이 됩니다.")
	@PostMapping("/{memberId}")
	public ResponseEntity<ApiResponse<Long>> saveProject(
		@Parameter(description = "사용자 ID(인증 객체 에러로 인한 임시 추가)") @PathVariable String memberId,
		 @Valid @RequestBody ProjectCreateRequest request) {

		Long projectId = projectService.createProject(memberId, request);

		boolean isDraft = request.isDraft();
		String message = isDraft ? "임시 저장 성공" : "프로젝트 생성 성공";
		HttpStatus status = isDraft ? HttpStatus.CREATED : HttpStatus.OK;
		return ResponseEntity.status(status).body(new ApiResponse<>(status.value(), message, projectId));
	}
}

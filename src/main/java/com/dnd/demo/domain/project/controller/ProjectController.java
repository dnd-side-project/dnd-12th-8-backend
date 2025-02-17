package com.dnd.demo.domain.project.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dnd.demo.common.dto.ApiResponse;
import com.dnd.demo.domain.project.dto.request.ProjectCreateRequest;
import com.dnd.demo.domain.project.dto.request.TemporaryProjectCreateRequest;
import com.dnd.demo.domain.project.dto.response.ProjectListResponseDto;
import com.dnd.demo.domain.project.dto.response.ProjectResponseDto;
import com.dnd.demo.domain.project.entity.Project;
import com.dnd.demo.domain.project.enums.Job;
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

	@Operation(summary = "프로젝트 전체 조회(인기 Post)", description = "찜 많은 순")
	@GetMapping("/popular")
	public ResponseEntity<Page<ProjectListResponseDto>> getPopularProjects(@PageableDefault(size = 10) Pageable pageable) {
		return ResponseEntity.ok(projectService.getPopularProjects(pageable));
	}

	@Operation(summary = "프로젝트 전체 조회(추천 Post)", description = "유저 온보딩 때 입력한 값에 따라 필터링")
	@GetMapping("/recommend")
	public ResponseEntity<Page<ProjectListResponseDto>> getRecommendedProjects(
		@AuthenticationPrincipal OAuthUserDetails oAuthUserDetails,
		@PageableDefault(size = 10) Pageable pageable
	) {
		return ResponseEntity.ok(projectService.getRecommendedProjects(oAuthUserDetails.getMemberId(), pageable));
	}

	@Operation(summary = "프로젝트 전체 조회(검색 Post)", description = "직무, 카테고리(다중) 선택 가능 " )
	@GetMapping("/search")
	public ResponseEntity<Page<ProjectListResponseDto>> searchProjects(
		@RequestParam(required = false) String query,
		@RequestParam(required = false) Job job,
		@RequestParam(required = false) List<Long> categories,
		@PageableDefault(size = 10) Pageable pageable
	) {
		return ResponseEntity.ok(projectService.searchProjects(query, job, categories, pageable));
	}

	@Operation(summary = "프로젝트 상세 조회", description = "현재는 최종 저장, 임시 저장 되어있는 글 모두 조회 됩니다.")
	@GetMapping("/{projectId}")
	public ResponseEntity<ProjectResponseDto> getProjectDetail(@PathVariable Long projectId) {
		return ResponseEntity.ok(projectService.getProjectDetail(projectId));
	}

	@Operation(summary = "프로젝트 삭제", description = "특정 프로젝트를 삭제합니다.")
	@DeleteMapping("/{projectId}")
	public ResponseEntity<ApiResponse<Void>> deleteProject(
		@PathVariable Long projectId,
		@AuthenticationPrincipal OAuthUserDetails oAuthUserDetails) {
		projectService.deleteProject(oAuthUserDetails.getMemberId(), projectId);
		return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "프로젝트 삭제 성공", null));
	}
}

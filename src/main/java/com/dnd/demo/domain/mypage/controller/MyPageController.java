package com.dnd.demo.domain.mypage.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dnd.demo.common.dto.ApiResponse;
import com.dnd.demo.domain.mypage.dto.MyPageProjectDetailResponseDto;
import com.dnd.demo.domain.mypage.dto.MyPageProjectResponseDto;
import com.dnd.demo.domain.mypage.service.MyPageService;
import com.dnd.demo.global.auth.dto.OAuthUserDetails;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "마이페이지 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/my-page")
@SecurityRequirement(name = "BearerAuth")
public class MyPageController {

	private final MyPageService myPageService;

	@Operation(summary = "마이페이지 프로젝트 조회", description = "마이페이지에 작성한 포스트 조회")
	@GetMapping("/project")
	public ResponseEntity<ApiResponse<List<MyPageProjectResponseDto>>> getProjectList(
		@AuthenticationPrincipal OAuthUserDetails oAuthUserDetails) {
		List<MyPageProjectResponseDto> myPageProjectResponseDtoList = myPageService.getMyPageProjectList(
			oAuthUserDetails.getMemberId());
		return ResponseEntity.ok(
			new ApiResponse<>(HttpStatus.OK.value(), "조회 성공", myPageProjectResponseDtoList));
	}

	@Operation(summary = "마이페이지 찜 프로젝트 조회", description = "마이페이지에 찜한 포스트 조회")
	@GetMapping("/favorite")
	public ResponseEntity<ApiResponse<List<MyPageProjectResponseDto>>> getFavoriteProjectList(
		@AuthenticationPrincipal OAuthUserDetails oAuthUserDetails) {
		List<MyPageProjectResponseDto> myPageProjectResponseDtoList = myPageService.getMyPageFavoriteList(
			oAuthUserDetails.getMemberId());
		return ResponseEntity.ok(
			new ApiResponse<>(HttpStatus.OK.value(), "조회 성공", myPageProjectResponseDtoList));
	}

	@Operation(summary = "마이페이지 임시저장 프로젝트 조회", description = "마이페이지 임시 저장 포스트 조회")
	@GetMapping("/temp-project")
	public ResponseEntity<ApiResponse<List<MyPageProjectResponseDto>>> getTempProjectList(
		@AuthenticationPrincipal OAuthUserDetails oAuthUserDetails) {
		List<MyPageProjectResponseDto> myPageProjectResponseDtoList = myPageService.getMyPageTempProjectList(
			oAuthUserDetails.getMemberId());
		return ResponseEntity.ok(
			new ApiResponse<>(HttpStatus.OK.value(), "조회 성공", myPageProjectResponseDtoList));
	}

	@Operation(summary = "임시 저장한 프로젝트 상세 조회", description = "사용자가 임시 저장한 프로젝트의 상세 정보를 조회합니다.")
	@GetMapping("/temp-project/{projectId}")
	public ResponseEntity<ApiResponse<MyPageProjectDetailResponseDto>> getTempProjectDetail(
		@AuthenticationPrincipal OAuthUserDetails oAuthUserDetails,
		@PathVariable Long projectId) {

		MyPageProjectDetailResponseDto projectDetail = myPageService.getMyPageTempProjectDetail(
			oAuthUserDetails.getMemberId(), projectId);

		return ResponseEntity.ok(
			new ApiResponse<>(HttpStatus.OK.value(), "임시 저장 프로젝트 상세 조회 성공", projectDetail));
	}

}

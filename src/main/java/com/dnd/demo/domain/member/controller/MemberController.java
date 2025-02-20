package com.dnd.demo.domain.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dnd.demo.domain.member.dto.request.OnboardingRequest;
import com.dnd.demo.domain.member.dto.request.OnboardingStatusRequest;
import com.dnd.demo.domain.member.dto.response.MemberResponse;
import com.dnd.demo.domain.member.dto.response.PointResponseDto;
import com.dnd.demo.domain.member.entity.Member;
import com.dnd.demo.domain.member.repository.MemberRepository;
import com.dnd.demo.domain.member.service.MemberService;
import com.dnd.demo.global.auth.dto.OAuthUserDetails;
import com.dnd.demo.global.exception.CustomException;
import com.dnd.demo.global.exception.ErrorCode;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "멤버 API")
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

	@Operation(summary = "나의 포인트 조회")
	@GetMapping("/points")
	public ResponseEntity<PointResponseDto> getUserPoints(
		@AuthenticationPrincipal OAuthUserDetails userDetails) {
		int points = memberService.getUserPoints(userDetails.getMemberId());
		return ResponseEntity.ok(new PointResponseDto(userDetails.getMemberId(), points));
	}

	@Operation(summary = "온보딩 정보 입력")
	@PostMapping("/onboarding/{memberId}")
	public ResponseEntity<String> completeOnboarding(
		@PathVariable String memberId,
		@RequestBody OnboardingRequest request) {
		memberService.completeOnboarding(memberId, request);
		return ResponseEntity.ok("온보딩 입력 정보 업데이트 완료");
	}

	@Operation(summary = "내 정보 조회 ")
	@GetMapping("/me")
	public ResponseEntity<MemberResponse> getMemberInfo(
		@AuthenticationPrincipal OAuthUserDetails userDetails) {
		MemberResponse response = memberService.getMemberInfo(userDetails.getMemberId());
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "온보딩 상태 변경", description = "사용자의 온보딩 완료 여부를 변경합니다.")
	@PatchMapping("/onboarding-status")
	public ResponseEntity<String> updateOnboardingStatus(
		@AuthenticationPrincipal OAuthUserDetails userDetails,
		@RequestParam boolean onboardingCompleted) {
		memberService.updateOnboardingStatus(userDetails.getMemberId(), onboardingCompleted);
		return ResponseEntity.ok("온보딩 상태 변경 완료");
	}

}

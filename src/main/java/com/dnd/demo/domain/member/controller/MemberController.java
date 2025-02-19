package com.dnd.demo.domain.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dnd.demo.domain.member.dto.response.PointResponseDto;
import com.dnd.demo.domain.member.service.MemberService;
import com.dnd.demo.global.auth.dto.OAuthUserDetails;

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
}

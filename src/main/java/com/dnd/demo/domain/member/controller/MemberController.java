package com.dnd.demo.domain.member.controller;


import com.dnd.demo.common.dto.ApiResponse;
import com.dnd.demo.domain.member.dto.MemberSearchRequestDto;
import com.dnd.demo.domain.member.dto.MemberSearchResponseDto;
import com.dnd.demo.domain.member.dto.TokenDto;
import com.dnd.demo.domain.member.service.MemberService;
import com.dnd.demo.global.auth.util.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "회원 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService memberService;

    @Value("${security.auth.header}")
    private String authHeader;

    // TODO) redis 도입하여 token invalidate 추가
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        // 리프레시 토큰 쿠키 삭제
        Cookie logoutCookie = new Cookie("refresh_token", null);
        logoutCookie.setHttpOnly(true);
        logoutCookie.setSecure(true);
        logoutCookie.setPath("/");
        logoutCookie.setMaxAge(0);
        response.addCookie(logoutCookie);

        return ResponseEntity.ok("Logged out successfully");
    }

    @GetMapping("/refresh")
    public ResponseEntity<?> refresh(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = jwtTokenProvider.resolveRefreshTokenFromCookie(request);
        if (refreshToken == null || !jwtTokenProvider.validateToken(refreshToken)) {
            return ResponseEntity.status(401).body("Invalid Refresh Token");
        }

        TokenDto tokenDto = jwtTokenProvider.reissueToken(request, refreshToken);
        Cookie refreshTokenCookie = jwtTokenProvider.getRefreshTokenCookie(tokenDto.refreshToken());
        response.addCookie(refreshTokenCookie);

        return ResponseEntity.ok()
          .header(authHeader, tokenDto.grantType() + " " + tokenDto.accessToken())
          .build();
    }

    // TODO) email 조회 GET/POST
    @PostMapping()
    @Operation(summary = "회원 검색", description = "프로젝트 생성 시 프로젝트 참여자 검색 API.")
    public ResponseEntity<ApiResponse<MemberSearchResponseDto>> getMember(
      @RequestBody MemberSearchRequestDto memberSearchRequestDto) {
        return ResponseEntity.ok(
          new ApiResponse<>(HttpStatus.OK.value(), "조회 성공",
            memberService.getMemberByEmail(memberSearchRequestDto)));

    }


}

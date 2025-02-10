package com.dnd.demo.domain.mypage.controller;

import com.dnd.demo.common.dto.ApiResponse;
import com.dnd.demo.domain.mypage.dto.MyPageProjectResponseDto;
import com.dnd.demo.domain.mypage.service.MyPageService;
import com.dnd.demo.global.auth.dto.OAuthUserDetails;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "마이페이지 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/my-page")
@SecurityRequirement(name = "BearerAuth")
public class MyPageController {

    private final MyPageService myPageService;

    @GetMapping("/project")
    public ResponseEntity<ApiResponse<List<MyPageProjectResponseDto>>> getProjectList(
      @AuthenticationPrincipal OAuthUserDetails oAuthUserDetails) {
        List<MyPageProjectResponseDto> myPageProjectResponseDtoList = myPageService.getMyPageProjectList(
          oAuthUserDetails.getMemberId());
        return ResponseEntity.ok(
          new ApiResponse<>(HttpStatus.OK.value(), "조회 성공", myPageProjectResponseDtoList));
    }

    @GetMapping("/favorite")
    public ResponseEntity<ApiResponse<List<MyPageProjectResponseDto>>> getFavoriteProjectList(
      @AuthenticationPrincipal OAuthUserDetails oAuthUserDetails) {
        List<MyPageProjectResponseDto> myPageProjectResponseDtoList = myPageService.getMyPageFavoriteList(
          oAuthUserDetails.getMemberId());
        return ResponseEntity.ok(
          new ApiResponse<>(HttpStatus.OK.value(), "조회 성공", myPageProjectResponseDtoList));
    }

    @GetMapping("/temp-project")
    public ResponseEntity<ApiResponse<List<MyPageProjectResponseDto>>> getTempProjectList(
      @AuthenticationPrincipal OAuthUserDetails oAuthUserDetails) {
        List<MyPageProjectResponseDto> myPageProjectResponseDtoList = myPageService.getMyPageTempProjectList(
          oAuthUserDetails.getMemberId());
        return ResponseEntity.ok(
          new ApiResponse<>(HttpStatus.OK.value(), "조회 성공", myPageProjectResponseDtoList));
    }


}

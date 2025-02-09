package com.dnd.demo.domain.member.controller;

import com.dnd.demo.common.dto.ApiResponse;
import com.dnd.demo.domain.member.dto.FavoriteRequestDto;
import com.dnd.demo.domain.member.service.FavoriteService;
import com.dnd.demo.global.auth.dto.OAuthUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "좋아요 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/favorite")
@SecurityRequirement(name = "BearerAuth")
public class FavoriteController {

    private final FavoriteService favoriteService;

    @Operation(summary = "찜하기", description = "프로젝트 찜하기 API.")
    @PostMapping
    public ResponseEntity<ApiResponse<Long>> addFavorite(
      @AuthenticationPrincipal OAuthUserDetails oAuthUserDetails,
      @RequestBody FavoriteRequestDto favoriteRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
          new ApiResponse<>(HttpStatus.CREATED.value(), "찜 성공",
            favoriteService.addFavorite(oAuthUserDetails.getMemberId(), favoriteRequestDto))
        );
    }

    @Operation(summary = "찜 취소", description = "프로젝트 찜 취소 API")
    @DeleteMapping
    public ResponseEntity<ApiResponse<Long>> cancelFavorite(
      @AuthenticationPrincipal OAuthUserDetails oAuthUserDetails,
      @RequestBody FavoriteRequestDto favoriteRequestDto) {
        return ResponseEntity.ok(
          new ApiResponse<>(HttpStatus.OK.value(), "프로젝트 찜 취소 성공",
            favoriteService.cancelFavorite(oAuthUserDetails.getMemberId(), favoriteRequestDto))
        );
    }
}

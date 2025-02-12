package com.dnd.demo.domain.member.controller;


import com.dnd.demo.common.dto.ApiResponse;
import com.dnd.demo.domain.member.dto.CommentRequestDto;
import com.dnd.demo.domain.member.service.CommentService;
import com.dnd.demo.global.auth.dto.OAuthUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "댓글 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/comments")
@SecurityRequirement(name = "BearerAuth")
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "댓글 생성", description = "댓글 생성 API.")
    @PostMapping
    public ResponseEntity<ApiResponse<Long>> addComment(
      @AuthenticationPrincipal OAuthUserDetails oAuthUserDetails,
      @RequestBody CommentRequestDto commentRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
          new ApiResponse<>(HttpStatus.CREATED.value(), "댓글 생성 성공",
            commentService.addComment(oAuthUserDetails.getMemberId(), commentRequestDto))
        );
    }

}

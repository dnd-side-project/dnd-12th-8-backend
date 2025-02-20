package com.dnd.demo.domain.feedback.controller;

import com.dnd.demo.common.dto.ApiResponse;
import com.dnd.demo.domain.feedback.dto.request.FeedbackResponseRequest;
import com.dnd.demo.domain.feedback.dto.response.FeedbackFormResponse;
import com.dnd.demo.domain.feedback.dto.response.FeedbackResultResponse;
import com.dnd.demo.domain.feedback.service.FeedbackFormService;
import com.dnd.demo.domain.feedback.service.FeedbackResponseService;
import com.dnd.demo.global.auth.dto.OAuthUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "피드백 폼 API", description = "프로젝트 피드백 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/feedback_form")
public class FeedbackFormController {

    private final FeedbackFormService feedbackFormService;
    private final FeedbackResponseService feedbackResponseService;

    @Operation(summary = "프로젝트 피드백 폼 조회", description = "사용자가 '피드백 하기' 버튼을 눌렀을 때 해당 프로젝트의 피드백 폼 데이터를 조회합니다.")
    @GetMapping("/{projectId}")
    public ResponseEntity<List<FeedbackFormResponse>> getFeedbackForms(
      @PathVariable Long projectId) {
        List<FeedbackFormResponse> feedbackForms = feedbackFormService.getFeedbackFormsByProjectId(
          projectId);
        return ResponseEntity.ok(feedbackForms);
    }

    @Operation(summary = "피드백 답변 제출", description = "프로젝트 피드백 답변 제출 API")
    @PostMapping("/feedback")
    public ResponseEntity<ApiResponse<String>> saveFeedbackForm(
      @AuthenticationPrincipal OAuthUserDetails oAuthUserDetails,
      @RequestBody FeedbackResponseRequest request) {
        String id = feedbackResponseService.save(oAuthUserDetails.getMemberId(), request);
        return ResponseEntity.status(HttpStatus.CREATED)
          .body(
            new ApiResponse<>(HttpStatus.CREATED.value(), id != null ? "피드백 제출 성공" : "피드백 제출 실패",
              id));
    }

    @Operation(summary = "프로젝트 피드백 결과 조회 ", description = "프로젝트 피드백 결과 조회 API")
    @GetMapping("/feedback-result/{projectId}")
    public ResponseEntity<ApiResponse<FeedbackResultResponse>> getFeedbackResult(
      @PathVariable("projectId") Long projectId) {
        return ResponseEntity.status(HttpStatus.OK)
          .body(new ApiResponse<>(HttpStatus.OK.value(), "결과 조회 성공",
            feedbackResponseService.getFeedbackResult(projectId)));
    }
}

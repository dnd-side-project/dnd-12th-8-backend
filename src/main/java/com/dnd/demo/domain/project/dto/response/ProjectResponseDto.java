package com.dnd.demo.domain.project.dto.response;

import com.dnd.demo.domain.Quiz.dto.response.QuizResponse;
import com.dnd.demo.domain.member.dto.CommentDto;
import com.dnd.demo.domain.member.dto.response.MemberSubInfoResponse;
import com.dnd.demo.domain.project.entity.Project;
import com.dnd.demo.domain.project.enums.Job;
import com.dnd.demo.domain.project.enums.Level;
import com.dnd.demo.domain.project.enums.ProjectStatus;
import java.util.List;

public record ProjectResponseDto(
  Long projectId,
  String title,
  String description,
  String dueDate,
  Integer favoriteCount,
  Integer participantCount,
  String thumbnailImgUrl,
  String logoImgUrl,
  String memberId,
  Job targetJob,
  Level targetLevel,
  Boolean isAdvertised,
  String projectMemberEmails,
  List<MemberSubInfoResponse> projectMembers,
  ProjectStatus projectStatus,
  PlatformCategoryResponse platformCategories,
  List<ProjectDetailResponse> projectDetails,
  List<QuizResponse> quizzes,
  List<CommentDto> comments
  // List<FeedbackFormResponse> feedbackForms
) {

    // 엔티티 → DTO 변환 메서드
    public static ProjectResponseDto from(
      Project project,
      PlatformCategoryResponse platformCategories,
      List<MemberSubInfoResponse> projectMembers,
      List<ProjectDetailResponse> projectDetails,
      List<QuizResponse> quizzes,
      List<CommentDto> comments
      // List<FeedbackFormResponse> feedbackForms
    ) {
        return new ProjectResponseDto(
          project.getProjectId(),
          project.getTitle(),
          project.getDescription(),
          project.getDueDate(),
          project.getFavoriteCount(),
          project.getParticipantCount(),
          project.getThumbnailImgUrl(),
          project.getLogoImgUrl(),
          project.getMemberId(),
          project.getTargetJob(),
          project.getTargetLevel(),
          project.getIsAdvertised(),
          project.getProjectMemberEmails(),
          projectMembers,
          project.getProjectStatus(),
          platformCategories,
          projectDetails,
          quizzes,
          comments
          // feedbackForms
        );
    }
}

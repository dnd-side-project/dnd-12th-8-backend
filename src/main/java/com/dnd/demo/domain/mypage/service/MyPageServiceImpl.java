package com.dnd.demo.domain.mypage.service;

import com.dnd.demo.domain.Quiz.dto.response.QuizResponse;
import com.dnd.demo.domain.Quiz.service.QuizService;
import com.dnd.demo.domain.feedback.dto.response.FeedbackFormResponse;
import com.dnd.demo.domain.feedback.entity.FeedbackForm;
import com.dnd.demo.domain.feedback.entity.FeedbackResponse;
import com.dnd.demo.domain.feedback.repository.FeedbackFormRepository;
import com.dnd.demo.domain.feedback.repository.FeedbackResponseRepository;
import com.dnd.demo.domain.feedback.service.FeedbackFormService;
import com.dnd.demo.domain.mypage.dto.MyPageProjectDetailResponseDto;
import com.dnd.demo.domain.mypage.dto.MyPageProjectResponseDto;
import com.dnd.demo.domain.project.dto.response.PlatformCategoryResponse;
import com.dnd.demo.domain.project.dto.response.ProjectDetailResponse;
import com.dnd.demo.domain.project.entity.Project;
import com.dnd.demo.domain.project.enums.ProjectStatus;
import com.dnd.demo.domain.project.repository.ProjectQueryDslRepository;
import com.dnd.demo.domain.project.repository.ProjectRepository;
import com.dnd.demo.domain.project.service.ProjectCategoryService;
import com.dnd.demo.domain.project.service.ProjectDetailService;
import com.dnd.demo.global.exception.CustomException;
import com.dnd.demo.global.exception.ErrorCode;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MyPageServiceImpl implements MyPageService {

    private final ProjectRepository projectRepository;
    private final ProjectQueryDslRepository projectQueryDslRepository;
    private final ProjectCategoryService projectCategoryService;
    private final ProjectDetailService projectDetailService;
    private final QuizService quizService;
    private final FeedbackFormService feedbackFormService;
    private final FeedbackResponseRepository feedbackResponseRepository;
    private final FeedbackFormRepository feedbackFormRepository;

    @Override
    public List<MyPageProjectResponseDto> getMyPageProjectList(String memberId) {
        return projectRepository.findByMemberId(memberId)
          .stream()
          .map(project -> {
              PlatformCategoryResponse categoryInfo = projectCategoryService.getCategoryInfoByProjectId(
                project.getProjectId());
              return MyPageProjectResponseDto.fromProject(project, categoryInfo);
          })
          .toList();
    }

    @Override
    public List<MyPageProjectResponseDto> getMyPageFavoriteList(String memberId) {
        return projectQueryDslRepository.findFavoriteProjectListByProjectId(memberId)
          .stream()
          .map(project -> {
              PlatformCategoryResponse categoryInfo = projectCategoryService.getCategoryInfoByProjectId(
                project.getProjectId());
              return MyPageProjectResponseDto.fromProject(project, categoryInfo);
          })
          .toList();
    }

    @Override
    public List<MyPageProjectResponseDto> getMyPageTempProjectList(String memberId) {
        return projectRepository.findByMemberIdAndProjectStatus(memberId, ProjectStatus.TEMPORARY)
          .stream()
          .map(project -> {
              PlatformCategoryResponse categoryInfo = projectCategoryService.getCategoryInfoByProjectId(
                project.getProjectId());
              return MyPageProjectResponseDto.fromProject(project, categoryInfo);
          })
          .toList();
    }

    @Override
    public List<MyPageProjectResponseDto> getMyPageParticipateProjectList(String memberId) {
        List<String> feedbackFormIdList = feedbackResponseRepository.findByMemberId(memberId)
          .stream().map(FeedbackResponse::getFormId).collect(Collectors.toList());

        List<Long> projectIdList = feedbackFormRepository.findByFeedbackFormIds(
            feedbackFormIdList).stream().map(FeedbackForm::getProjectId)
          .toList();
        return projectRepository.findByProjectIdIn(projectIdList)
          .stream()
          .map(project -> {
              PlatformCategoryResponse categoryInfo = projectCategoryService.getCategoryInfoByProjectId(
                project.getProjectId());
              return MyPageProjectResponseDto.fromProject(project, categoryInfo);
          })
          .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public MyPageProjectDetailResponseDto getMyPageTempProjectDetail(String memberId,
      Long projectId) {
        Project project = projectRepository.findByProjectIdAndMemberIdAndProjectStatus(projectId,
            memberId, ProjectStatus.TEMPORARY)
          .orElseThrow(() -> new CustomException(ErrorCode.TEMP_PROJECT_NOT_FOUND));

        PlatformCategoryResponse platformCategory = projectCategoryService.getPlatformCategoryByProjectId(
          projectId);
        List<ProjectDetailResponse> projectDetails = projectDetailService.getProjectDetailsByProjectId(
          projectId);
        List<QuizResponse> quizzes = quizService.getQuizzesWithOptionsByProjectId(projectId);
        List<FeedbackFormResponse> feedbackForms = feedbackFormService.getFeedbackFormsByProjectId(
          projectId);

        return MyPageProjectDetailResponseDto.from(project, platformCategory, projectDetails,
          quizzes, feedbackForms);
    }


}

package com.dnd.demo.domain.project.service;

import com.dnd.demo.domain.Quiz.dto.response.QuizResponse;
import com.dnd.demo.domain.Quiz.entity.Quiz;
import com.dnd.demo.domain.Quiz.service.QuizOptionService;
import com.dnd.demo.domain.Quiz.service.QuizService;
import com.dnd.demo.domain.advertisement.entity.Advertisement;
import com.dnd.demo.domain.advertisement.service.AdvertisementService;
import com.dnd.demo.domain.feedback.service.FeedbackFormService;
import com.dnd.demo.domain.member.service.MemberService;
import com.dnd.demo.domain.project.dto.request.ProjectCreateRequest;
import com.dnd.demo.domain.project.dto.request.ProjectSaveRequest;
import com.dnd.demo.domain.project.dto.request.TemporaryProjectCreateRequest;
import com.dnd.demo.domain.project.dto.response.AdvertisedProjectResponseDto;
import com.dnd.demo.domain.project.dto.response.PlatformCategoryResponse;
import com.dnd.demo.domain.project.dto.response.ProjectDetailResponse;
import com.dnd.demo.domain.project.dto.response.ProjectListResponseDto;
import com.dnd.demo.domain.project.dto.response.ProjectResponseDto;
import com.dnd.demo.domain.project.entity.Project;
import com.dnd.demo.domain.project.enums.Job;
import com.dnd.demo.domain.project.enums.ProjectStatus;
import com.dnd.demo.domain.project.repository.ProjectQueryDslRepository;
import com.dnd.demo.domain.project.repository.ProjectRepository;
import com.dnd.demo.global.exception.CustomException;
import com.dnd.demo.global.exception.ErrorCode;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectCategoryService projectCategoryService;
    private final ProjectDetailService projectDetailService;
    private final QuizService quizService;
    private final QuizOptionService quizOptionService;
    private final FeedbackFormService feedbackFormService;
    private final MemberService memberService;
    private final AdvertisementService advertisementService;
    private final ProjectQueryDslRepository projectQueryDslRepository;
    private final ProjectRepository projectRepository;

    @Transactional
    public Long saveTemporaryProject(String memberId, TemporaryProjectCreateRequest request) {
        Optional<Project> existingProject = projectQueryDslRepository.findLatestTemporaryProject(
          memberId);
        Project project = existingProject
          .map(existing -> {
              existing.updateFromRequest(request);
              return existing;
          })
          .orElseGet(() -> request.toEntity(memberId));

        existingProject.ifPresent(this::clearProjectData);
        saveProjectData(request, project);
        return project.getProjectId();
    }

    @Transactional
    public Long createFinalProject(String memberId, ProjectCreateRequest request) {
        memberService.reducePointsForProjectCreation(memberId, request.isAdvertised());

        Optional<Project> existingProject = projectQueryDslRepository.findLatestTemporaryProject(
          memberId);
        Project project = existingProject
          .map(existing -> {
              existing.updateFromRequest(request);
              existing.changeStatusToOpen();
              return existing;
          })
          .orElseGet(() -> request.toEntity(memberId));

        existingProject.ifPresent(this::clearProjectData);
        saveProjectData(request, project);

        advertisementService.createIfAdvertised(project.getProjectId(), request.isAdvertised());
        return project.getProjectId();
    }

    @Transactional(readOnly = true)
    public List<AdvertisedProjectResponseDto> getAdvertisedProjects() {
        List<Advertisement> advertisements = advertisementService.getAdvertisedProjects();

        List<Long> advertisedProjectIds = advertisements.stream()
          .map(Advertisement::getProjectId)
          .toList();

        Map<Long, Project> projectMap = projectRepository.findByProjectIdIn(advertisedProjectIds)
          .stream()
          .collect(Collectors.toMap(Project::getProjectId, Function.identity()));

        return advertisements.stream()
          .map(ad -> AdvertisedProjectResponseDto.from(ad, projectMap.get(ad.getProjectId())))
          .toList();
    }

    public Page<ProjectListResponseDto> getPopularProjects(Pageable pageable) {
        Page<Project> projects = projectQueryDslRepository.findPopularProjects(pageable);
        return projects.map(ProjectListResponseDto::from);
    }

    public Page<ProjectListResponseDto> getRecommendedProjects(String memberId, Pageable pageable) {
        Page<Project> projects = projectQueryDslRepository.findRecommendedProjects(memberId,
          pageable);
        return projects.map(ProjectListResponseDto::from);
    }

    public Page<ProjectListResponseDto> searchProjects(String query, Job job,
      List<Long> categoryIds,
      Pageable pageable) {
        Page<Project> projects = projectQueryDslRepository.searchProjects(query, job, categoryIds,
          pageable);
        return projects.map(ProjectListResponseDto::from);
    }

    @Transactional(readOnly = true)
    public ProjectResponseDto getProjectDetail(Long projectId) {
        Project project = getProject(projectId);
        PlatformCategoryResponse platformCategory = projectCategoryService.getPlatformCategoryByProjectId(
          projectId);
        List<ProjectDetailResponse> projectDetails = projectDetailService.getProjectDetailsByProjectId(
          projectId);
        List<QuizResponse> quizzes = quizService.getQuizzesWithOptionsByProjectId(projectId);
        // List<FeedbackFormResponse> feedbackForms = feedbackFormService.getFeedbackFormsByProjectId(projectId);
        return ProjectResponseDto.from(project, platformCategory, projectDetails, quizzes);
    }

    @Transactional
    public void deleteProject(String memberId, Long projectId) {
        Project project = getProject(projectId);
        validateProjectOwnership(memberId, project);
        clearProjectData(project);
        projectRepository.delete(project);
    }

    private void validateProjectOwnership(String memberId, Project project) {
        if (!project.getMemberId().equals(memberId)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_ACCESS);
        }
    }

    public Project getProject(Long projectId) {
        return projectRepository.findById(projectId)
          .orElseThrow(() -> new CustomException(ErrorCode.PROJECT_NOT_FOUND));
    }

    @Transactional
    public void clearProjectData(Project project) {
        projectCategoryService.deleteByProjectId(project);
        advertisementService.deleteByProjectId(project);
        projectDetailService.deleteByProjectId(project);
        List<Quiz> deletedQuizzes = quizService.deleteByProjectId(project);
        quizOptionService.deleteByQuizIds(deletedQuizzes);
        feedbackFormService.deleteByProjectId(project);
    }

    private void saveProjectData(ProjectSaveRequest request, Project project) {
        projectRepository.save(project);
        projectCategoryService.saveByProjectId(project, request.platformCategories());
        projectDetailService.saveByProjectId(project, request.projectDetailRequests());
        List<Quiz> quizzes = quizService.save(project, request.quizRequests());
        quizOptionService.save(quizzes, request.quizRequests());
        feedbackFormService.save(project, request.feedbackFormRequests());
    }

    private void validateProjectFinalUpload(Optional<Project> existingProject) {
        if (existingProject.isPresent()
          && existingProject.get().getProjectStatus() == ProjectStatus.OPEN) {
            throw new CustomException(ErrorCode.PROJECT_FINAL_CREATE_ALREADY_UPLOAD);
        }
    }
}

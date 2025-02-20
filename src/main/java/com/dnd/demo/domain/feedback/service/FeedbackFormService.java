package com.dnd.demo.domain.feedback.service;

import com.dnd.demo.domain.feedback.dto.request.FeedbackFormRequest;
import com.dnd.demo.domain.feedback.dto.response.FeedbackFormResponse;
import com.dnd.demo.domain.feedback.entity.FeedbackForm;
import com.dnd.demo.domain.feedback.entity.FeedbackQuestion;
import com.dnd.demo.domain.feedback.entity.FeedbackResult;
import com.dnd.demo.domain.feedback.entity.feedbackresult.FeedbackQuestionResult;
import com.dnd.demo.domain.feedback.repository.FeedbackFormRepository;
import com.dnd.demo.domain.feedback.repository.FeedbackResultRepository;
import com.dnd.demo.domain.project.entity.Project;
import com.dnd.demo.domain.project.enums.ProjectStatus;
import com.dnd.demo.domain.project.repository.ProjectRepository;
import com.dnd.demo.global.exception.CustomException;
import com.dnd.demo.global.exception.ErrorCode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FeedbackFormService {

    private final FeedbackFormRepository feedbackFormRepository;
    private final ProjectRepository projectRepository;

    private final FeedbackResultRepository feedbackResultRepository;

    @Transactional
    public void save(Project project, List<FeedbackFormRequest> requests) {
        List<FeedbackQuestion> feedbackQuestions = Optional.ofNullable(requests)
          .orElseGet(Collections::emptyList)
          .stream()
          .map(FeedbackFormRequest::toEntity)
          .toList();

        FeedbackForm feedbackForm = FeedbackForm.builder()
          .projectId(project.getProjectId())
          .questions(feedbackQuestions)
          .build();

        feedbackFormRepository.save(feedbackForm);

        FeedbackResult feedbackResult = new FeedbackResult();
        feedbackResult.setProjectId(project.getProjectId());

        Optional<FeedbackResult> existFeedbackResult = feedbackResultRepository.findByProjectId(
          project.getProjectId());
        existFeedbackResult.ifPresent(result -> feedbackResult.setId(result.getId()));

        List<FeedbackQuestionResult> feedbackQuestionResultList = new ArrayList<>();
        for (FeedbackQuestion question : feedbackForm.getQuestions()) {
            feedbackQuestionResultList.add(FeedbackQuestionResult.createQuestionResult(question));
        }
        feedbackResult.setFeedbackQuestionResult(feedbackQuestionResultList);
        feedbackResultRepository.save(feedbackResult);
    }

    public void deleteByProjectId(Project project) {
        feedbackFormRepository.deleteByProjectId(project.getProjectId());
    }

    @Transactional(readOnly = true)
    public List<FeedbackFormResponse> getFeedbackFormsByProjectIdForPublic(Long projectId) {
        validateProjectForPublicAccess(projectId);
        return feedbackFormRepository.findByProjectId(projectId)
            .stream()
            .flatMap(feedbackForm -> FeedbackFormResponse.from(feedbackForm).stream())
            .toList();
    }

    @Transactional(readOnly = true)
    public List<FeedbackFormResponse> getFeedbackFormsByProjectId(Long projectId) {
        return feedbackFormRepository.findByProjectId(projectId)
            .stream()
            .flatMap(feedbackForm -> FeedbackFormResponse.from(feedbackForm).stream())
            .toList();
    }

    private void validateProjectForPublicAccess(Long projectId) {
        Project project = projectRepository.findById(projectId)
            .orElseThrow(() -> new CustomException(ErrorCode.PROJECT_NOT_FOUND));

        if (project.getProjectStatus() != ProjectStatus.OPEN) {
            throw new CustomException(ErrorCode.PROJECT_NOT_OPEN);
        }
    }
}

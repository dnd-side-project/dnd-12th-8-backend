package com.dnd.demo.domain.feedback.service;

import com.dnd.demo.domain.feedback.dto.request.FeedbackResponseRequest;
import com.dnd.demo.domain.feedback.dto.response.FeedbackResultResponse;
import com.dnd.demo.domain.feedback.entity.FeedbackAnswer;
import com.dnd.demo.domain.feedback.entity.FeedbackForm;
import com.dnd.demo.domain.feedback.entity.FeedbackQuestion;
import com.dnd.demo.domain.feedback.entity.FeedbackResponse;
import com.dnd.demo.domain.feedback.entity.FeedbackResult;
import com.dnd.demo.domain.feedback.entity.feedbackresult.FeedbackQuestionResult;
import com.dnd.demo.domain.feedback.repository.FeedbackFormRepository;
import com.dnd.demo.domain.feedback.repository.FeedbackResponseRepository;
import com.dnd.demo.domain.feedback.repository.FeedbackResultRepository;
import com.dnd.demo.global.exception.CustomException;
import com.dnd.demo.global.exception.ErrorCode;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FeedbackResponseService {

    private final FeedbackFormRepository feedbackFormRepository;
    private final FeedbackResponseRepository feedbackResponseRepository;
    private final FeedbackResultRepository feedbackResultRepository;

    @Transactional
    public String save(String memberId, FeedbackResponseRequest request) {
        FeedbackForm feedbackForm = feedbackFormRepository.findByProjectId(request.projectId())
          .orElseThrow(() -> new CustomException(ErrorCode.FEEDBACK_FORM_NOT_FOUND));

        FeedbackResponse feedbackResponse = request.toEntity(memberId,
          feedbackForm.getId());
        feedbackResponseRepository.save(feedbackResponse);

        List<FeedbackAnswer> answers = feedbackResponse.getAnswers();

        FeedbackResult feedbackResult = feedbackResultRepository.findByProjectId(
            request.projectId())
          .orElseThrow(() -> new CustomException(ErrorCode.FEEDBACK_RESULT_NOT_FOUND));

        feedbackResult.setTotalResponseCount(feedbackResult.getTotalResponseCount() + 1);

        for (FeedbackAnswer answer : answers) {
            Optional<FeedbackQuestionResult> feedbackQuestionResult = feedbackResult.getFeedbackQuestionResult()
              .stream()
              .filter(
                questionAnswer -> answer.getQuestionId().equals(questionAnswer.getQuestionId()))
              .findFirst();

            if (feedbackQuestionResult.isEmpty()) {
                throw new CustomException(ErrorCode.INVALID_FEEDBACK_QUESTION_ID);
            }

            FeedbackQuestionResult questionAnswer = feedbackQuestionResult.get();
            questionAnswer.addQuestionResult(answer);
        }

        feedbackResultRepository.save(feedbackResult);
        return feedbackResponse.getId();
    }

    public FeedbackResultResponse getFeedbackResult(Long projectId) {
        // 1. FeedbackResult 가져오기
        FeedbackResult feedbackResult = feedbackResultRepository.findByProjectId(projectId)
            .orElseThrow(() -> new CustomException(ErrorCode.FEEDBACK_RESULT_NOT_FOUND));

        // 2. FeedbackForm 가져와서 질문 정보 매핑
        FeedbackForm feedbackForm = feedbackFormRepository.findByProjectId(projectId)
            .orElseThrow(() -> new CustomException(ErrorCode.FEEDBACK_FORM_NOT_FOUND));

        // 3. 질문 ID를 Key, 질문 내용을 Value로 매핑
        Map<String, String> questionTextMap = feedbackForm.getQuestions().stream()
            .collect(Collectors.toMap(FeedbackQuestion::getQuestionId, FeedbackQuestion::getQuestion));

        // 4. FeedbackResultResponse 생성 (질문 정보 포함)
        return FeedbackResultResponse.fromEntity(feedbackResult, questionTextMap);
    }

}

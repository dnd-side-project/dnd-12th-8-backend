package com.dnd.demo.domain.feedback.service;

import com.dnd.demo.domain.feedback.dto.request.FeedbackResponseRequest;
import com.dnd.demo.domain.feedback.dto.response.FeedbackResultResponse;
import com.dnd.demo.domain.feedback.entity.FeedbackAnswer;
import com.dnd.demo.domain.feedback.entity.FeedbackForm;
import com.dnd.demo.domain.feedback.entity.FeedbackResponse;
import com.dnd.demo.domain.feedback.entity.FeedbackResult;
import com.dnd.demo.domain.feedback.entity.feedbackresult.FeedbackQuestionResult;
import com.dnd.demo.domain.feedback.repository.FeedbackFormRepository;
import com.dnd.demo.domain.feedback.repository.FeedbackResponseRepository;
import com.dnd.demo.domain.feedback.repository.FeedbackResultRepository;
import java.util.List;
import java.util.Optional;
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
        Optional<FeedbackForm> feedbackForm = feedbackFormRepository.findByProjectId(
          request.projectId());
        if (feedbackForm.isPresent()) {
            FeedbackResponse feedbackResponse = request.toEntity(memberId,
              feedbackForm.get().getId());
            feedbackResponseRepository.save(feedbackResponse);

            List<FeedbackAnswer> answers = feedbackResponse.getAnswers();

            // 결과 변경
            Optional<FeedbackResult> existFeedbackResult = feedbackResultRepository.findByProjectId(
              request.projectId());
            if (existFeedbackResult.isPresent()) {

                FeedbackResult feedbackResult = existFeedbackResult.get();
                feedbackResult.setTotalResponseCount(feedbackResult.getTotalResponseCount() + 1);

                for (FeedbackAnswer answer : answers) {
                    Optional<FeedbackQuestionResult> feedbackQuestionResult = feedbackResult.getFeedbackQuestionResult()
                      .stream()
                      .filter(questionAnswer -> answer.getQuestionId()
                        .equals(questionAnswer.getQuestionId()))
                      .findFirst();

                    if (feedbackQuestionResult.isEmpty()) {
                        continue;
                    }

                    FeedbackQuestionResult questionAnswer = feedbackQuestionResult.get();
                    questionAnswer.addQuestionResult(answer);

                }
                feedbackResultRepository.save(feedbackResult);
            }
            return feedbackResponse.getId();
        } else {
            return null;
        }
    }

    public FeedbackResultResponse getFeedbackResult(Long projectId) {
        return FeedbackResultResponse.fromEntity(
          feedbackResultRepository.findByProjectId(projectId).orElseThrow());
    }
}

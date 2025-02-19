package com.dnd.demo.domain.feedback.service;

import com.dnd.demo.domain.feedback.dto.request.FeedbackResponseRequest;
import com.dnd.demo.domain.feedback.entity.FeedbackAnswer;
import com.dnd.demo.domain.feedback.entity.FeedbackForm;
import com.dnd.demo.domain.feedback.entity.FeedbackResponse;
import com.dnd.demo.domain.feedback.entity.FeedbackResult;
import com.dnd.demo.domain.feedback.entity.feedbackresult.FeedbackQuestionAnswer;
import com.dnd.demo.domain.feedback.repository.FeedbackFormRepository;
import com.dnd.demo.domain.feedback.repository.FeedbackResponseRepository;
import com.dnd.demo.domain.feedback.repository.FeedbackResultRepository;
import java.util.List;
import java.util.Map;
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
              feedbackForm.get().getFeedbackFormId());
            feedbackResponseRepository.save(feedbackResponse);

            // 폼 생성시에 결과도 미리 생성
            List<FeedbackAnswer> answers = feedbackResponse.getAnswers();

            // 결과 변경
            Optional<FeedbackResult> existFeedbackResult = feedbackResultRepository.findByProjectId(
              request.projectId());
            if (existFeedbackResult.isPresent()) {

                FeedbackResult feedbackResult = existFeedbackResult.get();
                feedbackResult.setTotalResponseCount(feedbackResult.getTotalResponseCount() + 1);
                Map<String, FeedbackQuestionAnswer> feedbackQuestionAnswerMap =
                  feedbackResult.getFeedbackQuestionAnswer();

                for (FeedbackAnswer answer : answers) {
                    FeedbackQuestionAnswer feedbackQuestionAnswer = feedbackQuestionAnswerMap.get(
                      answer.getQuestionId());
                    if (feedbackQuestionAnswer == null) {
                        continue;
                    }
                    switch (answer.getQuestionType()) {
                        case MULTIPLE_CHOICE:
                            feedbackQuestionAnswer.mutipleChoiceAdd(
                              Integer.parseInt(answer.getResponseText()) - 1);
                            break;

                        case SHORT_ANSWER:
                            feedbackQuestionAnswer.shortAnswerChoiceAdd(answer.getResponseText());
                            break;

                        case LIKERT_SCALE:
                            feedbackQuestionAnswer.likertScaleChoiceAdd(
                              Integer.parseInt(answer.getResponseText()));
                            break;

                        case AB_TEST:
                            break;
                    }
                }

            }

            return feedbackResponse.getId();
        } else {
            return null;
        }
    }
}

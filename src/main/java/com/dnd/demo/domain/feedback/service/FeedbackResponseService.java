package com.dnd.demo.domain.feedback.service;

import com.dnd.demo.domain.feedback.dto.request.FeedbackResponseRequest;
import com.dnd.demo.domain.feedback.entity.FeedbackForm;
import com.dnd.demo.domain.feedback.entity.FeedbackResponse;
import com.dnd.demo.domain.feedback.repository.FeedbackFormRepository;
import com.dnd.demo.domain.feedback.repository.FeedbackResponseRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FeedbackResponseService {

    private final FeedbackFormRepository feedbackFormRepository;
    private final FeedbackResponseRepository feedbackResponseRepository;

    @Transactional
    public String save(String memberId, FeedbackResponseRequest request) {
        Optional<FeedbackForm> feedbackForm = feedbackFormRepository.findByProjectId(
          request.projectId());
        if (feedbackForm.isPresent()) {
            FeedbackResponse feedbackResponse = request.toEntity(memberId,
              feedbackForm.get().getFeedbackFormId());
            feedbackResponseRepository.save(feedbackResponse);
            return feedbackResponse.getId();
        } else {
            return null;
        }
    }
}

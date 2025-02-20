package com.dnd.demo.domain.feedback.entity;

import jakarta.persistence.Id;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "feedback_responses")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeedbackResponse {

    @Id
    private String id;  // MongoDB의 ObjectId 자동 생성

    private String formId; // FeedbackForm의 ID
    private String memberId; // 응답한 사용자 ID
    private List<FeedbackAnswer> answers; // 응답 데이터 리스트
}

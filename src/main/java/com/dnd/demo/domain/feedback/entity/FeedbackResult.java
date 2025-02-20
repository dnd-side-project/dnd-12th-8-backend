package com.dnd.demo.domain.feedback.entity;


import com.dnd.demo.domain.feedback.entity.feedbackresult.FeedbackQuestionResult;
import jakarta.persistence.Id;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "feedback_result")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
public class FeedbackResult {

    @Id
    private ObjectId id;
    private Long projectId;
    private Integer totalResponseCount;
    // 응답기간, 사전 퀴즈 정답률
    private List<FeedbackQuestionResult> feedbackQuestionResult;
}

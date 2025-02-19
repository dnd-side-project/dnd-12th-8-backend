package com.dnd.demo.domain.feedback.entity.feedbackresult;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
public class FeedbackQuestionAnswer {

    private Integer totResponseCount;
    private Double avg;
    private Integer aResponseCount;
    private Integer bResponseCount;
    private List<FeedbackResultType> responseResultList;

    public void mutipleChoiceAdd(Integer choice) {
        totResponseCount += 1;
        responseResultList.get(choice).responseCount += 1;
    }

    public void shortAnswerChoiceAdd(String answer) {
        totResponseCount += 1;
        FeedbackResultType resultType = new FeedbackResultType();
        resultType.answerText = answer;
        responseResultList.add(resultType);
    }

    public void likertScaleChoiceAdd(Integer point) {
        totResponseCount += 1;
        responseResultList.get(point).responseCount += 1;
    }

//    public void abTestChoiceAdd(String) {
//        aResponseCount += 1;
//        bResponseCount += 1;
//        responseResultList.get(point).responseCount += 1;
//    }
}

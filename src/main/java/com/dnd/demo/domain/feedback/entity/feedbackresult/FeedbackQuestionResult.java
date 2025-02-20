package com.dnd.demo.domain.feedback.entity.feedbackresult;

import com.dnd.demo.domain.feedback.entity.FeedbackAnswer;
import com.dnd.demo.domain.feedback.entity.FeedbackQuestion;
import com.dnd.demo.domain.project.enums.QuestionType;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.ArrayList;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FeedbackQuestionResult {

    private String questionId;
    private QuestionType questionType;
    private Integer questionResponseCount;
    private Integer totalPoints;
    private Integer aResponseCount;
    private Integer bResponseCount;
    private List<FeedbackQuestionResultType> responseResultList;

    public void addQuestionResult(FeedbackAnswer answer) {
        switch (answer.getQuestionType()) {
            case MULTIPLE_CHOICE:
                questionResponseCount += 1;
                responseResultList.get(
                  Integer.parseInt(answer.getSelectedOption())).responseCount += 1;
                break;

            case SHORT_ANSWER:
                questionResponseCount += 1;
                FeedbackQuestionResultType resultType = new FeedbackQuestionResultType();
                resultType.answerText = answer.getResponseText();
                responseResultList.add(resultType);
                break;

            case LIKERT_SCALE:
                questionResponseCount += 1;
                int points = Integer.parseInt(answer.getSelectedOption()) + 1;
                responseResultList.get(points - 1).responseCount += 1;
                totalPoints += points;
                break;

            case AB_TEST:
                questionResponseCount += 1;
                if ("A".equals(answer.getSelectedOption())) {
                    aResponseCount += 1;
                } else {
                    bResponseCount += 1;
                }
                FeedbackQuestionResultType feedbackQuestionResultType = new FeedbackQuestionResultType();
                feedbackQuestionResultType.answerText = answer.getResponseText();
                responseResultList.add(feedbackQuestionResultType);
                break;

        }
    }

    public static FeedbackQuestionResult createQuestionResult(FeedbackQuestion question) {
        FeedbackQuestionResult feedbackQuestionResult = new FeedbackQuestionResult();
        feedbackQuestionResult.setQuestionId(question.getQuestionId());
        feedbackQuestionResult.setQuestionType(question.getType());
        feedbackQuestionResult.setQuestionResponseCount(0);
        List<FeedbackQuestionResultType> responseResultList = new ArrayList<>();
        switch (question.getType()) {
            case MULTIPLE_CHOICE:
            case LIKERT_SCALE:
                feedbackQuestionResult.setTotalPoints(0);
                for (String option : question.getOptions()) {
                    FeedbackQuestionResultType feedbackQuestionResultType = new FeedbackQuestionResultType();
                    feedbackQuestionResultType.setQuestionText(option);
                    feedbackQuestionResultType.setResponseCount(0);
                    responseResultList.add(feedbackQuestionResultType);
                }
                feedbackQuestionResult.setResponseResultList(responseResultList);
                break;

            case SHORT_ANSWER:
                feedbackQuestionResult.setResponseResultList(new ArrayList<>());
                break;

            case AB_TEST:
                feedbackQuestionResult.setAResponseCount(0);
                feedbackQuestionResult.setBResponseCount(0);
                feedbackQuestionResult.setResponseResultList(new ArrayList<>());
                break;
        }
        return feedbackQuestionResult;
    }
}

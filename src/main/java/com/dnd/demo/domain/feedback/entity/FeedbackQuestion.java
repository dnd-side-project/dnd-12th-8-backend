package com.dnd.demo.domain.feedback.entity;

import java.util.List;

import com.dnd.demo.domain.project.enums.QuestionType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeedbackQuestion {
	private String questionText;
	private QuestionType questionType;
	private List<String> options;
	private String abImageAUrl;
	private String abImageBUrl;
}

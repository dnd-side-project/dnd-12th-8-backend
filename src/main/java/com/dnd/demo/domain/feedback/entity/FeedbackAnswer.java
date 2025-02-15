package com.dnd.demo.domain.feedback.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeedbackAnswer {
	private String questionId;
	private String selectedOption; // 객관식 선택 값 (or 리커트 척도)
	private String responseText; // 주관식 or A/B 테스트 응답
}


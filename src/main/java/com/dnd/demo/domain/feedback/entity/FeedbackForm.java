package com.dnd.demo.domain.feedback.entity;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Document(collection = "feedback_form")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeedbackForm {

	@Id
	private String feedbackFormId;

	private Long projectId;

	@Field("questions")
	private List<FeedbackQuestion> questions;

}

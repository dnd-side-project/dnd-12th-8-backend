package com.dnd.demo.domain.feedback.entity;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Document(collection = "feedback_forms")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeedbackForm {

	@Id
	private String id;

	private Long projectId;
	private List<FeedbackQuestion> questions;
}

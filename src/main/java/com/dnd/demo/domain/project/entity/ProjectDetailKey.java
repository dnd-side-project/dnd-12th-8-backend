package com.dnd.demo.domain.project.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
public class ProjectDetailKey {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long detailId;

	private Long projectId;
}

package com.dnd.demo.domain.project.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;

@Entity
public class ProjectCategory {

	@Id
	private Long projectId;
	@Enumerated(EnumType.STRING)
	private Category category;
}

package com.dnd.demo.domain.project.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Advertisement {

	@Id
	private Long projectId;
	private String startDate;
	private String endDate;
}

package com.dnd.demo.domain.project.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ProjectCategory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long projectId;
	private Long categoryId;

	//정적 팩토리 메서드
	public static ProjectCategory create(Long projectId, Long categoryId) {
		return ProjectCategory.builder()
			.projectId(projectId)
			.categoryId(categoryId)
			.build();
	}
}

package com.dnd.demo.domain.project.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Status {
	TEMPORARY("임시저장"),
	OPEN("공개"),
	CLOSED("마감");

	private final String koreanName;
}

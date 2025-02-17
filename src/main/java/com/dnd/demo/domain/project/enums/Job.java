package com.dnd.demo.domain.project.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Job {
    DEVELOPER("개발자"),
    PLANNER("기획자"),
    DESIGNER("디자이너");

    private final String koreanName;
}

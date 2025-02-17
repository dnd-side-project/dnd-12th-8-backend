package com.dnd.demo.domain.project.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Level {
    LEARNER("학습자"),
    PROFESSIONAL("현직자");

    private final String koreanName;
}

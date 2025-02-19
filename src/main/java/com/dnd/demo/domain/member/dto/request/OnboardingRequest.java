package com.dnd.demo.domain.member.dto.request;

import java.util.List;
import java.util.stream.Collectors;

import com.dnd.demo.domain.member.entity.MemberCategory;
import com.dnd.demo.domain.project.enums.Job;
import com.dnd.demo.domain.project.enums.Level;

public record OnboardingRequest(
	String email,
	String nickname,
	Job job,
	Level level,
	List<Long> categoryIds
) {
	public List<MemberCategory> toEntity(String memberId, List<Long> categoryIds) {
		return categoryIds.stream()
			.map(categoryId -> new MemberCategory(memberId, categoryId))
			.collect(Collectors.toList());
	}
}

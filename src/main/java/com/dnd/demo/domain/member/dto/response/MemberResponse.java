package com.dnd.demo.domain.member.dto.response;

import java.util.List;

import com.dnd.demo.domain.member.entity.Member;
import com.dnd.demo.domain.member.entity.MemberRole;
import com.dnd.demo.domain.project.enums.Job;
import com.dnd.demo.domain.project.enums.Level;

public record MemberResponse(
	String memberId,
	String memberName,
	String email,
	Job job,
	Level level,
	Integer points,
	String profileUrl,
	boolean onboardingCompleted,
	MemberRole role,
	List<Long> categoryIds
) {
	public static MemberResponse from(Member member, List<Long> categoryIds) {
		return new MemberResponse(
			member.getMemberId(),
			member.getMemberName(),
			member.getEmail(),
			member.getJob(),
			member.getLevel(),
			member.getPoints(),
			member.getProfileUrl(),
			member.isOnboardingCompleted(),
			member.getRole(),
			categoryIds
		);
	}
}

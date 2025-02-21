package com.dnd.demo.domain.member.dto.response;

import com.dnd.demo.domain.member.entity.Member;
import com.dnd.demo.domain.project.enums.Job;
import com.dnd.demo.domain.project.enums.Level;

import lombok.Builder;

@Builder
public record MemberSearchResponseDto(
	String email,
	Job job,
	Level level,
	String profileUrl,
	String memberName
) {

	public static MemberSearchResponseDto fromEntity(Member member) {
		return MemberSearchResponseDto.builder()
			.email(member.getEmail())
			.job(member.getJob())
			.level(member.getLevel())
			.profileUrl(member.getProfileUrl())
			.memberName(member.getMemberName())
			.build();
	}
}

package com.dnd.demo.domain.member.dto;

import com.dnd.demo.domain.member.entity.Member;
import com.dnd.demo.domain.project.entity.Job;
import com.dnd.demo.domain.project.entity.Level;
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

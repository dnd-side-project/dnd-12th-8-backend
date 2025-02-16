package com.dnd.demo.domain.member.dto;

import com.dnd.demo.domain.member.entity.Member;
import com.dnd.demo.domain.project.entity.Job;
import com.dnd.demo.domain.project.entity.Level;
import lombok.Builder;

@Builder
public record MemberSearchResponseDto(
  String email,
  Job job,
  Level level
) {

    public static MemberSearchResponseDto fromEntity(Member member) {
        return MemberSearchResponseDto.builder()
          .email(member.getEmail())
          .job(member.getJob())
          .level(member.getLevel())
          .build();
    }

}

package com.dnd.demo.domain.member.dto.response;

import com.dnd.demo.domain.member.entity.Member;
import com.dnd.demo.domain.project.enums.Job;
import com.dnd.demo.domain.project.enums.Level;

public record MemberSubInfoResponse(
  String memberName,
  Job job,
  Level level,
  String profileUrl
) {

    public static MemberSubInfoResponse from(Member member) {
        return new MemberSubInfoResponse(
          member.getMemberName(),
          member.getJob(),
          member.getLevel(),
          member.getProfileUrl()
        );
    }
}

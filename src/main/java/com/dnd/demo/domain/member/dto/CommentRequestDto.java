package com.dnd.demo.domain.member.dto;

import com.dnd.demo.domain.member.entity.Comment;
import lombok.Getter;

@Getter
public class CommentRequestDto {

    private Long projectId;
    private String content;

    public Comment toEntity(String memberId) {
        return Comment.builder()
          .memberId(memberId)
          .projectId(projectId)
          .content(content)
          .build();
    }
}

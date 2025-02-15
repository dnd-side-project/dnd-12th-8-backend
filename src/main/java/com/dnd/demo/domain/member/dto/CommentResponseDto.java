package com.dnd.demo.domain.member.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentResponseDto {

    private Long projectId;

    private List<CommentDto> comments;

    public static CommentResponseDto fromCommentDto(Long projectId, List<CommentDto> comments) {
        return CommentResponseDto.builder()
          .projectId(projectId)
          .comments(comments)
          .build();
    }
}

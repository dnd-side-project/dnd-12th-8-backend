package com.dnd.demo.domain.member.dto.response;

import com.dnd.demo.domain.member.dto.CommentDto;
import java.util.List;
import lombok.Builder;


@Builder
public record CommentResponseDto(Long projectId, List<CommentDto> comments) {

    public static CommentResponseDto fromCommentDto(Long projectId, List<CommentDto> comments) {
        return CommentResponseDto.builder()
          .projectId(projectId)
          .comments(comments)
          .build();
    }
}

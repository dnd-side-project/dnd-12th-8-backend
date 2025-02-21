package com.dnd.demo.domain.member.service;

import com.dnd.demo.domain.member.dto.CommentRequestDto;
import com.dnd.demo.domain.member.dto.response.CommentResponseDto;

public interface CommentService {

    CommentResponseDto getProjectComments(Long projectId);

    Long addComment(String memberId, CommentRequestDto commentRequestDto);
}

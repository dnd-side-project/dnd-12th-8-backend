package com.dnd.demo.domain.member.service;

import com.dnd.demo.domain.member.dto.CommentDto;
import com.dnd.demo.domain.member.dto.CommentRequestDto;
import com.dnd.demo.domain.member.dto.CommentResponseDto;
import com.dnd.demo.domain.member.entity.Comment;
import com.dnd.demo.domain.member.repository.CommentRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentQueryDslRepository commentQueryDslRepository;

    @Override
    public CommentResponseDto getProjectComments(Long projectId) {
        List<CommentDto> commentDtoList = commentQueryDslRepository.findCommentListByProjectId(
          projectId);
        return CommentResponseDto.fromCommentDto(projectId, commentDtoList);
    }

    @Override
    @Transactional
    public Long addComment(String memberId, CommentRequestDto commentRequestDto) {
        Comment comment = commentRequestDto.toEntity(memberId);
        commentRepository.save(comment);

        return comment.getCommentId();
    }
}

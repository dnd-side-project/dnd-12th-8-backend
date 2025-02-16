package com.dnd.demo.domain.member.service;

import com.dnd.demo.domain.member.dto.CommentDto;
import java.util.List;

public interface CommentQueryDslRepository {

    public List<CommentDto> findCommentListByProjectId(Long projectId);
}

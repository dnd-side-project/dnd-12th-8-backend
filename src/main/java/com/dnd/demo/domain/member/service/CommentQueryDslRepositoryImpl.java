package com.dnd.demo.domain.member.service;


import com.dnd.demo.domain.member.dto.CommentDto;
import com.dnd.demo.domain.member.entity.QComment;
import com.dnd.demo.domain.member.entity.QMember;
import com.dnd.demo.domain.project.entity.QProject;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentQueryDslRepositoryImpl implements CommentQueryDslRepository {

    private final JPAQueryFactory queryFactory;
    private final QProject project = QProject.project;
    private final QMember member = QMember.member;
    private final QComment comment = QComment.comment;

    @Override
    public List<CommentDto> findCommentListByProjectId(Long projectId) {
        return queryFactory.select(
            Projections.constructor(CommentDto.class, member.memberId,
              member.memberName,
              member.job, member.level, comment.content)
          )
          .from(project)
          .join(comment)
          .on(project.projectId.eq(comment.projectId))
          .join(member)
          .on(comment.memberId.eq(member.memberId))
          .where(project.projectId.eq(projectId))
          .orderBy(comment.createdAt.asc())
          .fetch();
    }
}

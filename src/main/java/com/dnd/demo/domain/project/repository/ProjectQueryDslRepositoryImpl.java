package com.dnd.demo.domain.project.repository;

import com.dnd.demo.domain.member.entity.QFavorite;
import com.dnd.demo.domain.project.entity.Project;
import com.dnd.demo.domain.project.entity.QProject;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProjectQueryDslRepositoryImpl implements ProjectQueryDslRepository {

    private final JPAQueryFactory queryFactory;
    private final QProject project = QProject.project;
    private final QFavorite favorite = QFavorite.favorite;

    @Override
    public List<Project> findFavoriteProjectListByProjectId(String memberId) {
        return queryFactory.select(project)
          .from(project)
          .join(favorite)
          .on(project.id.eq(favorite.projectId))
          .where(favorite.memberId.eq(memberId))
          .orderBy(favorite.createdAt.desc())
          .fetch();
    }
}

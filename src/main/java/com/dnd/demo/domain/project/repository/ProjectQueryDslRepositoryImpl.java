package com.dnd.demo.domain.project.repository;

import com.dnd.demo.domain.member.entity.Member;
import com.dnd.demo.domain.member.entity.QFavorite;
import com.dnd.demo.domain.member.entity.QMember;
import com.dnd.demo.domain.project.entity.Project;
import com.dnd.demo.domain.project.entity.QProject;
import com.dnd.demo.domain.project.entity.QProjectCategory;
import com.dnd.demo.domain.project.enums.Job;
import com.dnd.demo.domain.project.enums.ProjectStatus;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
          .on(project.projectId.eq(favorite.projectId))
          .where(favorite.memberId.eq(memberId))
          .orderBy(favorite.createdAt.desc())
          .fetch();
    }

    @Override
    public Page<Project> findPopularProjects(Pageable pageable) {
        List<Project> projects = queryFactory
            .selectFrom(project)
            .leftJoin(favorite).on(project.projectId.eq(favorite.projectId))
            .where(project.projectStatus.eq(ProjectStatus.OPEN))
            .groupBy(project.projectId)
            .orderBy(
                favorite.count().desc(),
                project.dueDate.asc(),
                project.createdAt.desc()
            )
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        return new PageImpl<>(projects, pageable, projects.size());
    }


    @Override
    public Page<Project> findRecommendedProjects(String memberId, Pageable pageable) {
        Member member = queryFactory
            .selectFrom(QMember.member)
            .where(QMember.member.memberId.eq(memberId))
            .fetchOne();

        if (member == null) return Page.empty();

        List<Project> projects = queryFactory
            .selectFrom(QProject.project)
            .leftJoin(QProjectCategory.projectCategory)
            .on(QProject.project.projectId.eq(QProjectCategory.projectCategory.projectId))
            .where(
                QProject.project.projectStatus.eq(ProjectStatus.OPEN),
                QProject.project.targetJob.eq(member.getJob()),
                QProject.project.targetLevel.eq(member.getLevel()),
                QProjectCategory.projectCategory.categoryId.in(getCategoryIds(memberId))
            )
            .orderBy(
                QProject.project.dueDate.asc(),
                QProject.project.createdAt.desc()
            )
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        return new PageImpl<>(projects, pageable, projects.size());
    }


    @Override
    public Page<Project> searchProjects(String query, Job job, List<Long> categoryIds, Pageable pageable) {
        BooleanBuilder where = new BooleanBuilder();

        where.and(QProject.project.projectStatus.eq(ProjectStatus.OPEN));

        if (query != null && !query.isBlank()) {
            where.and(QProject.project.title.containsIgnoreCase(query)
                .or(QProject.project.description.containsIgnoreCase(query)));
        }

        if (job != null) {
            where.and(QProject.project.targetJob.eq(job));
        }

        if (categoryIds != null && !categoryIds.isEmpty()) {
            where.and(QProjectCategory.projectCategory.categoryId.in(categoryIds));
        }

        List<Project> projects = queryFactory
            .selectFrom(QProject.project)
            .leftJoin(QProjectCategory.projectCategory)
            .on(QProject.project.projectId.eq(QProjectCategory.projectCategory.projectId))
            .where(where)
            .orderBy(
                QProject.project.dueDate.asc(),
                QProject.project.createdAt.desc()
            )
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        return new PageImpl<>(projects, pageable, projects.size());
    }


    private List<Long> getCategoryIds(String memberId) {
        return queryFactory
            .select(QProjectCategory.projectCategory.categoryId)
            .from(QProjectCategory.projectCategory)
            .where(QProjectCategory.projectCategory.projectId.in(
                JPAExpressions.select(QProject.project.projectId)
                    .from(QProject.project)
                    .where(QProject.project.memberId.eq(memberId))
            ))
            .fetch();
    }

    @Override
    public Optional<Project> findLatestTemporaryProjectByMemberId(String memberId) {
        Project result = queryFactory
            .selectFrom(project)
            .where(
                project.memberId.eq(memberId),
                project.projectStatus.eq(ProjectStatus.TEMPORARY)
            )
            .orderBy(project.createdAt.desc())
            .fetchFirst();

        return Optional.ofNullable(result);
    }
}

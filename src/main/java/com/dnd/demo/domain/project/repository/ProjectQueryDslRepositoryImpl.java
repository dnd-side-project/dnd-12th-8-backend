package com.dnd.demo.domain.project.repository;

import static com.dnd.demo.domain.project.entity.QProjectCategory.*;

import com.dnd.demo.domain.member.entity.Member;
import com.dnd.demo.domain.member.entity.QFavorite;
import com.dnd.demo.domain.member.entity.QMember;
import com.dnd.demo.domain.member.entity.QMemberCategory;
import com.dnd.demo.domain.project.entity.Project;
import com.dnd.demo.domain.project.entity.QProject;
import com.dnd.demo.domain.project.entity.QProjectCategory;
import com.dnd.demo.domain.project.enums.Job;
import com.dnd.demo.domain.project.enums.ProjectStatus;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.Collections;
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
    private final QMember member = QMember.member;

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

        if (member == null) {
            return Page.empty();
        }

        List<Long> categoryIds = getCategoryIds(memberId);

        if (categoryIds.isEmpty()) {
            return Page.empty();
        }

        List<Long> projectIds = queryFactory
          .select(QProjectCategory.projectCategory.projectId)
          .from(QProjectCategory.projectCategory)
          .where(QProjectCategory.projectCategory.categoryId.in(categoryIds))
          .fetch();

        if (projectIds.isEmpty()) {
            return Page.empty();
        }

        List<Project> projects = queryFactory
          .selectFrom(QProject.project)
          .where(
            QProject.project.projectStatus.eq(ProjectStatus.OPEN),
            QProject.project.targetJob.eq(member.getJob()),
            QProject.project.targetLevel.eq(member.getLevel()),
            QProject.project.projectId.in(projectIds)
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
    public Page<Project> searchProjects(String query, Job job, List<Long> categoryIds,
        Pageable pageable) {
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

        //서브쿼리에서 먼저 project_id 조회
        List<Long> projectIds = queryFactory
            .select(QProject.project.projectId)
            .from(QProject.project)
            .where(where)
            .orderBy(QProject.project.dueDate.asc(), QProject.project.createdAt.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        if (projectIds.isEmpty()) {
            return new PageImpl<>(Collections.emptyList(), pageable, 0);
        }

        //서브쿼리 결과를 활용하여 실제 프로젝트 데이터 조회
        List<Project> projects = queryFactory
            .selectFrom(QProject.project)
            .where(QProject.project.projectId.in(projectIds))
            .fetch();

        //전체 데이터 개수 조회
        Long total = queryFactory
            .select(QProject.project.projectId.countDistinct())
            .from(QProject.project)
            .leftJoin(QProjectCategory.projectCategory)
            .on(QProject.project.projectId.eq(QProjectCategory.projectCategory.projectId))
            .where(where)
            .fetchOne();

        return new PageImpl<>(projects, pageable, Optional.ofNullable(total).orElse(0L));
    }


    private List<Long> getCategoryIds(String memberId) {
        return queryFactory
          .select(QMemberCategory.memberCategory.categoryId)
          .from(QMemberCategory.memberCategory)
          .where(QMemberCategory.memberCategory.memberId.eq(memberId))
          .fetch();
    }

    @Override
    public Optional<Project> findLatestTemporaryProject(String memberId) {
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

    @Override
    public List<Long> findProjectIdsByCategoryIds(List<Long> categoryIds) {
        return queryFactory
            .select(projectCategory.projectId)
            .from(projectCategory)
            .join(project).on(projectCategory.projectId.eq(project.projectId))
            .where(
                projectCategory.categoryId.in(categoryIds),
                project.projectStatus.eq(ProjectStatus.OPEN)
            )
            .fetch();
    }

}

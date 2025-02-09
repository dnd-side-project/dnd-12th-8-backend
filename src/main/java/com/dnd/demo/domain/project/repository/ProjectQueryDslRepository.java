package com.dnd.demo.domain.project.repository;

import java.util.List;

import com.dnd.demo.domain.project.entity.Project;

public interface ProjectQueryDslRepository {

	public List<Project> findFavoriteProjectListByProjectId(String memberId);
}

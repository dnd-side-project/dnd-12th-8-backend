package com.dnd.demo.domain.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dnd.demo.domain.project.entity.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
}

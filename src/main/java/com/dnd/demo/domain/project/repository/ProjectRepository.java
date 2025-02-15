package com.dnd.demo.domain.project.repository;

import com.dnd.demo.domain.project.entity.Project;
import com.dnd.demo.domain.project.entity.Status;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findByMemberId(String memberId);

    List<Project> findByMemberIdAndStatus(String memberId, Status status);
}

package com.dnd.demo.domain.Quiz.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dnd.demo.domain.Quiz.entity.Quiz;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
	List<Quiz> deleteByProjectId(Long id);

	List<Quiz> findByProjectId(Long projectId);
}

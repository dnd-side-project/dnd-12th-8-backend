package com.dnd.demo.domain.Quiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dnd.demo.domain.Quiz.entity.QuizCompletion;

@Repository
public interface QuizCompletionRepository extends JpaRepository<QuizCompletion, Long> {
	boolean existsByMemberIdAndProjectId(String memberId, Long projectId);
}

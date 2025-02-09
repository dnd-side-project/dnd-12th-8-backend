package com.dnd.demo.domain.Quiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dnd.demo.domain.Quiz.entity.QuizOption;

@Repository
public interface QuizOptionRepository extends JpaRepository<QuizOption, Long> {
}

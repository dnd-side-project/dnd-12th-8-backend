package com.dnd.demo.domain.feedback.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.dnd.demo.domain.feedback.entity.FeedbackForm;

@Repository
public interface FeedbackFormRepository extends MongoRepository<FeedbackForm, String> {

	void deleteByProjectId(Long id);

	List<FeedbackForm> findByProjectId(Long projectId);
}

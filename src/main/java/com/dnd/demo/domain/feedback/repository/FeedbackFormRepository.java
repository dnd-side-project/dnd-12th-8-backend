package com.dnd.demo.domain.feedback.repository;

import com.dnd.demo.domain.feedback.entity.FeedbackForm;

import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackFormRepository extends MongoRepository<FeedbackForm, String> {

    void deleteByProjectId(Long id);

    List<FeedbackForm> findAllByProjectId(Long projectId);

    Optional<FeedbackForm> findByProjectId(Long projectId);

}

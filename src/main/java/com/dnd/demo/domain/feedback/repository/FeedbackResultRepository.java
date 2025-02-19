package com.dnd.demo.domain.feedback.repository;

import com.dnd.demo.domain.feedback.entity.FeedbackResult;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackResultRepository extends MongoRepository<FeedbackResult, String> {


    Optional<FeedbackResult> findByProjectId(Long projectId);
}

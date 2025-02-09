package com.dnd.demo.domain.feedback.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.dnd.demo.domain.feedback.entity.FeedbackForm;

@Repository
public interface FeedbackFormRepository extends MongoRepository<FeedbackForm, String> {

}

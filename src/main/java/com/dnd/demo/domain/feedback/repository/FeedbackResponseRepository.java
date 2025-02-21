package com.dnd.demo.domain.feedback.repository;

import com.dnd.demo.domain.feedback.entity.FeedbackResponse;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackResponseRepository extends MongoRepository<FeedbackResponse, String> {


    List<FeedbackResponse> findByMemberId(String memberId);
}

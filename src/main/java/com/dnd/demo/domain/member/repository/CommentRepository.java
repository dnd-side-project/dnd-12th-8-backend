package com.dnd.demo.domain.member.repository;

import com.dnd.demo.domain.member.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, String> {

}

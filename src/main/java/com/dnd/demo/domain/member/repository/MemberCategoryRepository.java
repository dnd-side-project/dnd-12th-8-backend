package com.dnd.demo.domain.member.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dnd.demo.domain.member.entity.MemberCategory;

@Repository
public interface MemberCategoryRepository extends JpaRepository<MemberCategory, Long> {
	List<MemberCategory> findByMemberId(String memberId);
}

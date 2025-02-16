package com.dnd.demo.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dnd.demo.domain.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, String> {
}

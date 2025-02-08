package com.dnd.demo.domain.member.repository;

import com.dnd.demo.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRespository extends JpaRepository<Member, String> {

}

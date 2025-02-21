package com.dnd.demo.domain.member.repository;

import com.dnd.demo.domain.member.entity.Member;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String> {

    List<Member> findByEmailIn(Collection<String> emails);

    Optional<Member> findByEmail(String email);

}

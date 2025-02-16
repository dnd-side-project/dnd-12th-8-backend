package com.dnd.demo.domain.member.repository;

import com.dnd.demo.domain.member.entity.Favorite;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    Optional<Favorite> findByMemberIdAndProjectId(String memberId, Long projectId);

    Long deleteByMemberIdAndProjectId(String memberId, Long projectId);
}

package com.dnd.demo.domain.member.entity;

import com.dnd.demo.domain.member.dto.FavoriteRequestDto;
import com.dnd.demo.global.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Favorite extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long favoriteId;

    @Column(nullable = false)
    private String memberId;

    @Column(nullable = false)
    private Long projectId;

    public static Favorite fromFavoriteRequestDto(FavoriteRequestDto requestDto) {
        return Favorite.builder()
          .memberId(requestDto.getMemberId())
          .projectId(requestDto.getProjectId())
          .build();
    }
}

package com.dnd.demo.domain.member.dto;


import com.dnd.demo.domain.member.entity.Favorite;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FavoriteRequestDto {

    private Long projectId;

    public Favorite toEntity(String memberId) {
        return Favorite.builder()
          .memberId(memberId)
          .projectId(this.projectId)
          .build();
    }
}

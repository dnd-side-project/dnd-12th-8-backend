package com.dnd.demo.domain.member.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FavoriteRequestDto {

    private Long projectId;
    private String memberId;
}

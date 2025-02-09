package com.dnd.demo.domain.member.service;

import com.dnd.demo.domain.member.dto.FavoriteRequestDto;

public interface FavoriteService {

    public Long addFavorite(String memberId, FavoriteRequestDto favoriteRequestDto);

    public Long cancelFavorite(String memberId, FavoriteRequestDto favoriteRequestDto);
}

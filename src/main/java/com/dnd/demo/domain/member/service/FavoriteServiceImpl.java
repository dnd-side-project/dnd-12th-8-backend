package com.dnd.demo.domain.member.service;

import com.dnd.demo.domain.member.dto.FavoriteRequestDto;
import com.dnd.demo.domain.member.entity.Favorite;
import com.dnd.demo.domain.member.repository.FavoriteRepository;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteRepository favoriteRepository;

    @Override
    @Transactional
    public Long addFavorite(String memberId, FavoriteRequestDto favoriteRequestDto) {
        Favorite favorite = favoriteRequestDto.toEntity(memberId);

        Optional<Favorite> existFavorite = favoriteRepository.findByMemberIdAndProjectId(
          favorite.getMemberId(), favorite.getProjectId());

        if (existFavorite.isEmpty()) {
            favoriteRepository.save(favorite);
        }

        return favorite.getProjectId();
    }

    @Override
    @Transactional
    public Long cancelFavorite(String memberId, FavoriteRequestDto favoriteRequestDto) {
        return favoriteRepository.deleteByMemberIdAndProjectId(memberId,
          favoriteRequestDto.getProjectId());
    }
}

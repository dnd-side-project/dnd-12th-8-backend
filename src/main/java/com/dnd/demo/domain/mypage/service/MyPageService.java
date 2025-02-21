package com.dnd.demo.domain.mypage.service;

import com.dnd.demo.domain.mypage.dto.MyPageProjectDetailResponseDto;
import com.dnd.demo.domain.mypage.dto.MyPageProjectResponseDto;
import java.util.List;

public interface MyPageService {

    public List<MyPageProjectResponseDto> getMyPageProjectList(String memberId);

    public List<MyPageProjectResponseDto> getMyPageFavoriteList(String memberId);

    public List<MyPageProjectResponseDto> getMyPageTempProjectList(String memberId);

    public List<MyPageProjectResponseDto> getMyPageParticipateProjectList(String memberId);

    MyPageProjectDetailResponseDto getMyPageTempProjectDetail(String memberId, Long projectId);

}

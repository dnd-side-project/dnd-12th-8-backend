package com.dnd.demo.domain.mypage.service;

import java.util.List;

import com.dnd.demo.domain.mypage.dto.MyPageProjectDetailResponseDto;
import com.dnd.demo.domain.mypage.dto.MyPageProjectResponseDto;

public interface MyPageService {
	public List<MyPageProjectResponseDto> getMyPageProjectList(String memberId);

	public List<MyPageProjectResponseDto> getMyPageFavoriteList(String memberId);

	public List<MyPageProjectResponseDto> getMyPageTempProjectList(String memberId);

	MyPageProjectDetailResponseDto getMyPageTempProjectDetail(String memberId, Long projectId);
}

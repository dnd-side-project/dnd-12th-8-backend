package com.dnd.demo.domain.mypage.service;

import com.dnd.demo.domain.mypage.dto.MyPageProjectResponseDto;
import com.dnd.demo.domain.project.entity.Status;
import com.dnd.demo.domain.project.repository.ProjectQueryDslRepository;
import com.dnd.demo.domain.project.repository.ProjectRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MyPageServiceImpl implements MyPageService {

    private final ProjectRepository projectRepository;
    private final ProjectQueryDslRepository projectQueryDslRepository;

    @Override
    public List<MyPageProjectResponseDto> getMyPageProjectList(String memberId) {
        return projectRepository.findByMemberId(memberId)
          .stream().map(MyPageProjectResponseDto::fromProject)
          .toList();
    }

    @Override
    public List<MyPageProjectResponseDto> getMyPageFavoriteList(String memberId) {
        return projectQueryDslRepository.findFavoriteProjectListByProjectId(memberId)
          .stream().map(MyPageProjectResponseDto::fromProject)
          .toList();
    }

    @Override
    public List<MyPageProjectResponseDto> getMyPageTempProjectList(String memberId) {
        return projectRepository.findByMemberIdAndStatus(memberId, Status.TEMP)
          .stream().map(MyPageProjectResponseDto::fromProject)
          .toList();
    }
}

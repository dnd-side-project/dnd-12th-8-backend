package com.dnd.demo.domain.mypage.dto;

import com.dnd.demo.domain.project.entity.Project;
import com.dnd.demo.domain.project.enums.Job;
import com.dnd.demo.domain.project.enums.Level;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MyPageProjectResponseDto {

    private Long projectId;
    private String title;
    private String logoImgUrl;
    private String thumbnailImgUrl;
    private String description;
    private String dueDate;
    private Job targetJob;
    private Level targetLevel;

    public static MyPageProjectResponseDto fromProject(Project project) {
        return MyPageProjectResponseDto.builder()
          .projectId(project.getProjectId())
          .title(project.getTitle())
          .logoImgUrl(project.getLogoImgUrl())
          .thumbnailImgUrl(project.getThumbnailImgUrl())
          .description(project.getDescription())
          .dueDate(project.getDueDate())
          .targetJob(project.getTargetJob())
          .targetLevel(project.getTargetLevel())
          .build();
    }
}

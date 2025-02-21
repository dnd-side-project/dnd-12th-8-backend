package com.dnd.demo.domain.mypage.dto;

import java.util.List;

import com.dnd.demo.domain.project.dto.response.PlatformCategoryResponse;
import com.dnd.demo.domain.project.entity.Project;
import com.dnd.demo.domain.project.entity.ProjectCategory;
import com.dnd.demo.domain.project.enums.Job;
import com.dnd.demo.domain.project.enums.PlatformType;

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
    private Job job;
    private PlatformCategoryResponse platformCategoryResponse;

    public static MyPageProjectResponseDto fromProject(Project project, PlatformCategoryResponse platformCategoryResponse) {
        return MyPageProjectResponseDto.builder()
            .projectId(project.getProjectId())
            .title(project.getTitle())
            .logoImgUrl(project.getLogoImgUrl())
            .thumbnailImgUrl(project.getThumbnailImgUrl())
            .description(project.getDescription())
            .dueDate(project.getDueDate())
            .job(project.getTargetJob())
            .platformCategoryResponse(platformCategoryResponse)
            .build();
    }
}




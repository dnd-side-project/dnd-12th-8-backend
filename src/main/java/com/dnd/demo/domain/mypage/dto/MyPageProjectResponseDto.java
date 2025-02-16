package com.dnd.demo.domain.mypage.dto;

import com.dnd.demo.domain.project.entity.Project;
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

    public static MyPageProjectResponseDto fromProject(Project project) {
        return MyPageProjectResponseDto.builder()
          .projectId(project.getId())
          .title(project.getTitle())
          .logoImgUrl(project.getLogoImgUrl())
          .thumbnailImgUrl(project.getThumbnailImgUrl())
          .description(project.getDescription())
          .build();
    }
}

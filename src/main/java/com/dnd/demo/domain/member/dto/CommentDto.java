package com.dnd.demo.domain.member.dto;


import com.dnd.demo.domain.project.enums.Job;
import com.dnd.demo.domain.project.enums.Level;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentDto {

    private String memberId;
    private String memberName;
    private Job job;
    private Level level;
    private String content;
}

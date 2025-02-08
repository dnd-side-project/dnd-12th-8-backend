package com.dnd.demo.domain.member.entity;

import com.dnd.demo.domain.project.entity.Category;
import com.dnd.demo.domain.project.entity.Job;
import com.dnd.demo.domain.project.entity.Level;
import com.dnd.demo.global.auth.dto.OAuthUserDetails;
import com.dnd.demo.global.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member extends BaseEntity {

    @Id
    private String memberId;

    @Enumerated(EnumType.STRING)
    private Job job;
    @Enumerated(EnumType.STRING)
    private Level level;
    @Enumerated(EnumType.STRING)
    private Category category;
    private String email;
    private Integer points;
    private String memberName;
    private String profileUrl = null;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    public static Member fromOAuthUserDetails(OAuthUserDetails oAuthUserDetails) {
        return Member.builder()
          .memberId(oAuthUserDetails.getMemberId())
          .memberName(oAuthUserDetails.getMemberName())
          .profileUrl(oAuthUserDetails.getProfileUrl())
          .email(oAuthUserDetails.getEmail())
          .role(oAuthUserDetails.getRole())
          .build();
    }
}

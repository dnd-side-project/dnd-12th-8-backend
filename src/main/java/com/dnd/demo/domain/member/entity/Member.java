package com.dnd.demo.domain.member.entity;

import com.dnd.demo.domain.project.enums.Job;
import com.dnd.demo.domain.project.enums.Level;
import com.dnd.demo.global.common.entity.BaseEntity;
import com.dnd.demo.global.exception.CustomException;
import com.dnd.demo.global.exception.ErrorCode;

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
    private String email;
    private Integer points ;
    private String memberName;
    private String profileUrl;
    private boolean onboardingCompleted = false;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    public void addPoints(int amount) {
        this.points = (this.points == null ? 0 : this.points) + amount;
    }

    public void reducePoints(int amount) {
        if (this.points < amount) {
            throw new CustomException(ErrorCode.INSUFFICIENT_POINTS);
        }
        this.points -= amount;
    }

    public void updateOnboarding(String email, String nickname, Job job, Level level) {
        this.email = email;
        this.memberName = nickname;
        this.job = job;
        this.level = level;
    }

    public void updateOnboardingCompleted() {
        this.onboardingCompleted = true;
    }

    public void updateOnboardingStatus(boolean onboardingCompleted) {
        this.onboardingCompleted = onboardingCompleted;
    }
}

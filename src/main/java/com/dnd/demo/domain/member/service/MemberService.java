package com.dnd.demo.domain.member.service;

import com.dnd.demo.domain.member.dto.request.OnboardingRequest;
import com.dnd.demo.domain.member.dto.response.MemberResponse;
import com.dnd.demo.domain.member.dto.response.MemberSubInfoResponse;
import com.dnd.demo.domain.member.entity.Member;
import com.dnd.demo.domain.member.entity.MemberCategory;
import com.dnd.demo.domain.member.repository.MemberCategoryRepository;
import com.dnd.demo.domain.member.repository.MemberRepository;
import com.dnd.demo.domain.project.repository.CategoryQueryDslRepository;
import com.dnd.demo.global.exception.CustomException;
import com.dnd.demo.global.exception.ErrorCode;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberCategoryRepository memberCategoryRepository;
    private final CategoryQueryDslRepository categoryQueryDslRepository;

    private static final int PROJECT_CREATION_COST = 100;
    private static final int PROJECT_CREATION_AD_COST = 400;
    private static final int QUIZ_COMPLETION_REWARD = 100;
    private static final int ONBOARDING_COMPLETION_REWARD = 100;

    public Member getMember(String id) {
        return memberRepository.findById(id)
          .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public int getUserPoints(String memberId) {
        return memberRepository.findById(memberId)
          .map(Member::getPoints)
          .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
    }

    @Transactional
    public Member addPoints(String memberId, int points) {
        Member member = getMember(memberId);
        member.addPoints(points);
        memberRepository.save(member);
        return member;
    }

    @Transactional
    public void reducePoints(String memberId, int amount) {
        Member member = getMember(memberId);
        member.reducePoints(amount);
        memberRepository.save(member);
    }

    @Transactional
    public void reducePointsForProjectCreation(String memberId, boolean isAdvertised) {
        int requiredPoints = isAdvertised ? PROJECT_CREATION_AD_COST : PROJECT_CREATION_COST;
        reducePoints(memberId, requiredPoints);
    }

    @Transactional
    public Member rewardForQuizCompletion(String memberId) {
        return addPoints(memberId, QUIZ_COMPLETION_REWARD);
    }

    @Transactional
    public void completeOnboarding(String memberId, OnboardingRequest request) {
        Member member = getMember(memberId);
        member.updateOnboarding(request.email(), request.nickname(), request.job(),
          request.level());
        member.updateOnboardingCompleted();
        member.addPoints(ONBOARDING_COMPLETION_REWARD);
        memberRepository.save(member);

        List<Long> selectedCategoryIds = request.categoryIds();
        List<Long> allCategoryIds = categoryQueryDslRepository.findRelatedCategoryIds(
          selectedCategoryIds);

        List<MemberCategory> newCategories = request.toEntity(memberId, allCategoryIds);
        memberCategoryRepository.saveAll(newCategories);
    }

    public MemberResponse getMemberInfo(String memberId) {
        Member member = getMember(memberId);

        List<Long> categoryIds = memberCategoryRepository.findByMemberId(memberId).stream()
          .map(MemberCategory::getCategoryId)
          .collect(Collectors.toList());

        return MemberResponse.from(member, categoryIds);
    }

    public List<MemberSubInfoResponse> getMemberInfoByEmails(List<String> email) {
        return memberRepository.findByEmailIn(email).stream()
          .map(MemberSubInfoResponse::from).collect(Collectors.toList());
    }

    @Transactional
    public void updateOnboardingStatus(String memberId, boolean onboardingCompleted) {
        Member member = getMember(memberId);
        member.updateOnboardingStatus(onboardingCompleted);
        memberRepository.save(member);
    }
}

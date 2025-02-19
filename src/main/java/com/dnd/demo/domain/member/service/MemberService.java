package com.dnd.demo.domain.member.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dnd.demo.global.exception.CustomException;
import com.dnd.demo.global.exception.ErrorCode;
import com.dnd.demo.domain.member.entity.Member;
import com.dnd.demo.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;

	private static final int PROJECT_CREATION_COST = 100;
	private static final int PROJECT_CREATION_AD_COST = 400;
	private static final int QUIZ_COMPLETION_REWARD = 100;

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
}

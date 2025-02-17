package com.dnd.demo.domain.member.service;

import org.springframework.stereotype.Service;

import com.dnd.demo.global.exception.CustomException;
import com.dnd.demo.global.exception.ErrorCode;
import com.dnd.demo.domain.member.entity.Member;
import com.dnd.demo.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;

	public Member getMember(String id){
		return memberRepository.findById(id)
			.orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
	}
}

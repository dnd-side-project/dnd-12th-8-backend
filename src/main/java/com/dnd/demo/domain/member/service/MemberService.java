package com.dnd.demo.domain.member.service;


import com.dnd.demo.domain.member.dto.MemberSearchRequestDto;
import com.dnd.demo.domain.member.dto.MemberSearchResponseDto;

public interface MemberService {

    public MemberSearchResponseDto getMemberByEmail(MemberSearchRequestDto memberSearchRequestDto);

}

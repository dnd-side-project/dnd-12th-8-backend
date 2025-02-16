package com.dnd.demo.domain.member.service;


import com.dnd.demo.domain.member.dto.MemberSearchRequestDto;
import com.dnd.demo.domain.member.dto.MemberSearchResponseDto;
import com.dnd.demo.domain.member.entity.Member;
import com.dnd.demo.domain.member.repository.MemberRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public MemberSearchResponseDto getMemberByEmail(MemberSearchRequestDto memberSearchRequestDto) {
        Optional<Member> existMember = memberRepository.findByEmail(memberSearchRequestDto.email());
        return existMember.map(MemberSearchResponseDto::fromEntity).orElse(null);
    }
}

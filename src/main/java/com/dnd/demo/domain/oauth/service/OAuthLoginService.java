package com.dnd.demo.domain.oauth.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.dnd.demo.domain.member.entity.Member;
import com.dnd.demo.domain.member.entity.MemberRole;
import com.dnd.demo.domain.member.repository.MemberRepository;
import com.dnd.demo.domain.oauth.domain.oauth.OAuthLoginParams;
import com.dnd.demo.domain.oauth.domain.oauth.OAuthUserInfo;
import com.dnd.demo.global.auth.dto.OAuthUserDetails;
import com.dnd.demo.global.auth.util.JwtTokenProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class OAuthLoginService {

    private final MemberRepository memberRepository;
    private final RequestOAuthInfoService requestOAuthInfoService;
    private final JwtTokenProvider jwtTokenProvider;

    public String login(OAuthLoginParams params) {
        OAuthUserInfo oAuthUserInfo = requestOAuthInfoService.request(params);
        String memberId = oAuthUserInfo.getId().toString();
        Optional<Member> existingMember = memberRepository.findById(memberId);

        Member member;
        if (existingMember.isPresent()) {
            member = existingMember.get();
        } else {
            member = Member.builder()
                .memberId(memberId)
                .email(oAuthUserInfo.getEmail())
                .memberName(oAuthUserInfo.getName())
                .profileUrl(oAuthUserInfo.getProfileImageUrl())
                .role(MemberRole.PRE_MEMBER)
                .build();

            memberRepository.save(member);
        }

        OAuthUserDetails userDetails = OAuthUserDetails.fromMember(member);
        return jwtTokenProvider.createToken(userDetails.getMemberId(), userDetails.getAuthorities());
    }
}

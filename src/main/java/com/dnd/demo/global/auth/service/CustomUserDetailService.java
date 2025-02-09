package com.dnd.demo.global.auth.service;

import java.util.Optional;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.dnd.demo.domain.member.entity.Member;
import com.dnd.demo.domain.member.entity.MemberRole;
import com.dnd.demo.domain.member.repository.MemberRepository;
import com.dnd.demo.global.auth.dto.OAuthUserDetails;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class CustomUserDetailService extends DefaultOAuth2UserService {

	private final MemberRepository memberRespository;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User = super.loadUser(userRequest);
		String clientRegistrationId = userRequest.getClientRegistration().getRegistrationId();
		OAuthUserDetails oAuthUserDetails = OAuthUserDetails.fromSocialLogin(clientRegistrationId,
			oAuth2User);

		Optional<Member> existMember = memberRespository.findById(oAuthUserDetails.getMemberId());
		if (existMember.isPresent()) {
			return OAuthUserDetails.fromMember(existMember.get());
		} else {
			oAuthUserDetails.setRole(MemberRole.PRE_MEMBER);
			Member signUpMember = Member.fromOAuthUserDetails(oAuthUserDetails);
			memberRespository.save(signUpMember);
			return OAuthUserDetails.fromMember(signUpMember);
		}
	}
}

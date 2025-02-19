package com.dnd.demo.global.auth.handler;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.dnd.demo.domain.member.entity.Member;
import com.dnd.demo.domain.member.repository.MemberRepository;
import com.dnd.demo.global.auth.util.JwtTokenProvider;
import com.dnd.demo.global.exception.CustomException;
import com.dnd.demo.global.exception.ErrorCode;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuthSuccessHandler implements AuthenticationSuccessHandler {

	private final JwtTokenProvider jwtTokenProvider;
	private final MemberRepository memberRepository;

	@Value("${security.auth.header}")
	private String authHeader;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException, ServletException {
		String memberId = authentication.getName();
		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

		String accessToken = jwtTokenProvider.createToken(memberId, authentication.getAuthorities());
		response.setHeader(authHeader, "Bearer " + accessToken);

		SecurityContextHolder.getContext().setAuthentication(authentication);

		if (member.isOnboardingCompleted()) {
			response.sendRedirect("/dashboard.html");
		} else {
			response.sendRedirect("/onboarding.html");
		}
	}
}

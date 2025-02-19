package com.dnd.demo.domain.oauth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dnd.demo.domain.oauth.dto.response.TokenResponse;
import com.dnd.demo.domain.oauth.infrastructure.kakao.KakaoLoginParam;
import com.dnd.demo.domain.oauth.service.OAuthLoginService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "카카오 로그인 자동 리다이렉트 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth")
@Slf4j
public class OAuthController {

    private final OAuthLoginService oAuthLoginService;

    @Operation(summary = "카카오 인증 코드 받아서 인증 수행하고 Member DB에 저장하고 AT 발급")
    @GetMapping("/kakao")
    public ResponseEntity<TokenResponse> kakaoLogin(@RequestParam("code") String code) {
        KakaoLoginParam param = new KakaoLoginParam(code);
        String accessToken = oAuthLoginService.login(param);
        TokenResponse tokenResponse = new TokenResponse(accessToken);
        return ResponseEntity.ok(tokenResponse);
    }
}

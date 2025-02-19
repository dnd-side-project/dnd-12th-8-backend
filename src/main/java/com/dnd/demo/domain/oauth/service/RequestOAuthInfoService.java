package com.dnd.demo.domain.oauth.service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.dnd.demo.domain.oauth.domain.oauth.OAuthClient;
import com.dnd.demo.domain.oauth.domain.oauth.OAuthLoginParams;
import com.dnd.demo.domain.oauth.domain.oauth.OAuthProvider;
import com.dnd.demo.domain.oauth.domain.oauth.OAuthUserInfo;

@Service
public class RequestOAuthInfoService {

    private final Map<OAuthProvider, OAuthClient> clients;

    public RequestOAuthInfoService(List<OAuthClient> clients) {
        this.clients = clients.stream().collect(
            Collectors.toUnmodifiableMap(OAuthClient::oauthProvider, Function.identity())
        );
    }

    // OAuthClient 인터페이스를 구현한 KaKaoApiClient 클래스를 사용하여 카카오 서버와 통신
    public OAuthUserInfo request(OAuthLoginParams params) {
        OAuthClient client = clients.get(params.oAuthProvider());
        String accessToken = client.requestAccessToken(params);
        return client.requestOAuthInfo(accessToken);
    }
}

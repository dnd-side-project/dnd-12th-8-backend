package com.dnd.demo.domain.oauth.service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.dnd.demo.domain.oauth.domain.oauth.OAuthClient;
import com.dnd.demo.domain.oauth.domain.oauth.OAuthProvider;
import com.dnd.demo.domain.oauth.domain.oauth.OAuthUserInfo;

@Service
public class RequestOAuthUnlinkService {

    private final Map<OAuthProvider, OAuthClient> clients;

    public RequestOAuthUnlinkService(List<OAuthClient> clients) {
        this.clients = clients.stream().collect(
            Collectors.toUnmodifiableMap(OAuthClient::oauthProvider, Function.identity())
        );
    }

    public void request(OAuthUserInfo oAuthUserInfo) {
        OAuthClient client = clients.get(oAuthUserInfo.getOAuthProvider());
        client.unlinkOauthUser(oAuthUserInfo.getId());
    }
}

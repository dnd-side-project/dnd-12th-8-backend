package com.dnd.demo.domain.oauth.domain.oauth;

public interface OAuthClient {

    OAuthProvider oauthProvider();

    String requestAccessToken(OAuthLoginParams params);

    OAuthUserInfo requestOAuthInfo(String accessToken);

    void unlinkOauthUser(Long socialUserId);
}

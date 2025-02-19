package com.dnd.demo.domain.oauth.domain.oauth;

public interface OAuthUserInfo {

    Long getId();

    String getEmail();

    String getName();

    String getNickname();

    String getProfileImageUrl();

    OAuthProvider getOAuthProvider();
}


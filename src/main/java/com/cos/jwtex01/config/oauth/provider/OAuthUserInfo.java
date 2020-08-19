package com.cos.jwtex01.config.oauth.provider;

//필요한 값들 넣어둠
public interface OAuthUserInfo {
	String getProviderId();
	String getProvider();
	String getEmail();
	String getName();
}

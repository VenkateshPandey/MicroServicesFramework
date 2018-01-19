package com.tekBase.androidMs.security;

import java.util.LinkedHashMap;

import com.tekBase.androidMs.model.AuthTokenInfo;

public interface AuthManager {
	public AuthTokenInfo getTokens();
	public LinkedHashMap<String, Object> validateToken(String access_token);
}

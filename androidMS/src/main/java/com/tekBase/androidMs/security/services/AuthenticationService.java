package com.tekBase.androidMs.security.services;

import java.util.LinkedHashMap;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import com.tekBase.androidMs.model.ApiResponse;
import com.tekBase.androidMs.model.AuthTokenInfo;
import com.tekBase.androidMs.security.AuthManager;

@Component
public class AuthenticationService {

	@Autowired
	AuthManager authManager;

	@Autowired
	@Qualifier("logger")
	Logger logger;

	public String getAccessToken() {

		AuthTokenInfo tokenInfo = authManager.getTokens();
		return tokenInfo.getAccess_token();
	}

	public ApiResponse validateAccessToken(String accessToken) {
		LinkedHashMap<String, Object> validityMap = authManager.validateToken(accessToken);
        ApiResponse apiResp=new ApiResponse();
		
		if(validityMap.containsKey("valid")) {
			apiResp.setStatusCode(200);
			apiResp.setStatusMsg("valid");			
			return apiResp;
		}
		else if(validityMap.containsKey("error")) {	
			logger.info("Error received from downstream for access_token : " +validityMap.get("error_description"));
			apiResp.setStatusCode(401);
			apiResp.setStatusMsg("Invalid Token");
			return apiResp;
		}
		else return apiResp;

	}
}

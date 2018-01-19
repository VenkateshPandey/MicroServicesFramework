package com.tekBase.androidMs.security.services;

import java.util.Arrays;
import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.tekBase.androidMs.model.AuthTokenInfo;
import com.tekBase.androidMs.model.ClientError;
import com.tekBase.androidMs.security.AuthManager;
import com.tekBase.androidMs.security.ErrorHandler;

@PropertySource("classpath:/application-security.properties")
@Component
public class AuthInfoFromAD implements AuthManager{
	
    @Value("${auth.server.uri}")
    private String AUTH_SERVER_URI ;
	
	@Value("${validate.token}")
	private String VALIDATE_TOKEN;
	 
	@Value("${client.credential}")
	private String clientCred;
    
	RestTemplate restTemplate;
	ErrorHandler errorhandler;
	public AuthInfoFromAD()
	{
		restTemplate = new RestTemplate(); 
		errorhandler=new ErrorHandler();
	    restTemplate.setErrorHandler(errorhandler);		 
	}

    private HttpHeaders getHeaders(){
    	HttpHeaders headers = new HttpHeaders();
    	headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    	headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    	return headers;
    }
    
   
    private HttpHeaders getHeadersWithClientCredentials(){
    	String base64ClientCredentials = new String(Base64.encode(clientCred.getBytes()));
    	HttpHeaders headers = getHeaders();
    	headers.add("Authorization", "Basic " + base64ClientCredentials);
    	return headers;
    }    
    
	@Override
    @SuppressWarnings("unchecked")
	public  AuthTokenInfo getTokens(){
    	AuthTokenInfo tokenInfo = null;
        RestTemplate restTemplate = new RestTemplate(); 
        MultiValueMap<String, String> map2= new LinkedMultiValueMap<String, String>();
               map2.add("grant_type", "password");
               map2.add("username", "ankrishn");
               map2.add("password", "Thisis@07");
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map2,getHeadersWithClientCredentials());              
        ResponseEntity<Object> response = restTemplate.exchange(AUTH_SERVER_URI, HttpMethod.POST, request, Object.class);
        System.out.println("Received status : "+ response.getStatusCode() +" and body : "+response.getBody());
        LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>)response.getBody();
        
        
        if(map!=null){
        	tokenInfo = new AuthTokenInfo();
        	tokenInfo.setAccess_token((String)map.get("access_token"));
        	tokenInfo.setToken_type((String)map.get("token_type"));
        	tokenInfo.setRefresh_token((String)map.get("refresh_token"));
        	tokenInfo.setExpires_in((int)map.get("expires_in"));
        	tokenInfo.setScope((String)map.get("scope"));
        	System.out.println(tokenInfo);
        }else{
            
        }
        return tokenInfo;
    }
	@SuppressWarnings("unchecked")
	@Override
	public LinkedHashMap<String, Object> validateToken(String access_token)
	{
	    LinkedHashMap<String, Object> map=new LinkedHashMap<>();	 
		ResponseEntity<Object> response = restTemplate.getForEntity(VALIDATE_TOKEN+access_token, Object.class);
		if(response.getStatusCode() ==HttpStatus.OK) {
	     map = (LinkedHashMap<String, Object>)response.getBody();
		}
		else {
			ClientError error=errorhandler.getError();
			map.put("error", error.getError());
			map.put("error_description",error.getError_description());
		}
	     
	     return map;
	}
    
}

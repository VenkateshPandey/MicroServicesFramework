package com.tekBase.androidMs.controllers;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.tekBase.androidMs.config.ServiceExecutionTime;
import com.tekBase.androidMs.model.ApiResponse;
import com.tekBase.androidMs.security.services.AuthenticationService;

@RestController
public class ApiTestController {
	
	@Autowired
	@Qualifier("logger")
    Logger logger;
	
	@Autowired
	AuthenticationService authService;
	
	@ServiceExecutionTime
	@RequestMapping("/rest/{id}")
	@Cacheable("name")
	@HystrixCommand(fallbackMethod = "fallbackFindAll")
	public ResponseEntity<ApiResponse> findAll(@PathVariable(name="id") String id, @RequestHeader(value="access_token") String access_token) {
		
		logger.info("Log message generated for : " +id +" and access_token is " + access_token );
		ApiResponse apiResp=authService.validateAccessToken(access_token);
		if(apiResp.getStatusCode()==200) {
			logger.info("Received response 200  ");
			apiResp.setResponseData("Hello " + id + "  !!" );
			
			return new ResponseEntity<ApiResponse>(apiResp,HttpStatus.OK);
		}
		
		return new ResponseEntity<ApiResponse>(apiResp,HttpStatus.UNAUTHORIZED);
			
	}
	
	
	public ResponseEntity<ApiResponse> fallbackFindAll(@PathVariable(name="id") String id, @RequestHeader(value="access_token") String access_token,Throwable t) {
		
		logger.info("Error generated for : " +id +" and access_token is " + access_token +" is : "+ t);
		ApiResponse apiResp=authService.validateAccessToken(access_token);
		/*if(apiResp.getStatusCode()==200) {
			logger.info("Received response 200  ");
			apiResp.setResponseData("Hello " + id + "  !!" );
			
			return new ResponseEntity<ApiResponse>(apiResp,HttpStatus.OK);
		}
		*/
		return new ResponseEntity<ApiResponse>(apiResp,HttpStatus.BAD_REQUEST);
			
	}
	

}

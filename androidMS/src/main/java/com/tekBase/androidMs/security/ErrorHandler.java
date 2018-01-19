package com.tekBase.androidMs.security;

import java.io.IOException;
import java.nio.charset.Charset;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatus.Series;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.DefaultResponseErrorHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tekBase.androidMs.model.ClientError;

public class ErrorHandler extends DefaultResponseErrorHandler {
	
	public ClientError error; 
	
	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
		
		if(hasError(response.getStatusCode()))
		{   
			String bodyText = StreamUtils.copyToString(response.getBody(), Charset.defaultCharset());
			ObjectMapper oMapper=new ObjectMapper();
			error=oMapper.readValue(bodyText, ClientError.class);
			
		}
		}
	public boolean hasError(HttpStatus statusCode)
	{
		return (statusCode.series() == Series.SERVER_ERROR ||statusCode.series() == Series.CLIENT_ERROR);
	}	
	public  ClientError getError()
	{
		return error;
	}
	
}

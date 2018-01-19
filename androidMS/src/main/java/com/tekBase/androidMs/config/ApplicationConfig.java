package com.tekBase.androidMs.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;


@Configuration
public class ApplicationConfig {
	
	@Bean
	@Qualifier("logger")
	public Logger getLoggerBean()
	{
		return LoggerFactory.getLogger(this.getClass());
	}
    
	@Bean
	@Qualifier("restTemplate")
	RestTemplate getRestTemplate()
	{
		return new RestTemplate();
	}
	
	/*@Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }*/
}

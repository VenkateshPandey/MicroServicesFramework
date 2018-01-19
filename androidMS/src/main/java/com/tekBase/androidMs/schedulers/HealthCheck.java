package com.tekBase.androidMs.schedulers;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class HealthCheck {
	 
	 @Autowired
	 @Qualifier("logger")
     Logger logger;
     
	 @Autowired
	 RestTemplate restTemplate;
    
	// @Scheduled(fixedRate = 10000)
	@Scheduled(cron = "${cron.expression}")
	public void sendHealthMonitoringReport() {
		
		logger.info("This is scheduled task");
		String json=restTemplate.getForObject("http://localhost:2223/metrics", String.class);
		logger.info("##HealthCheck : " + json);
		//EmailService.sendSimpleMessage("rupadhayaya@teksystems.com", "Health Monitoring Status",json);
		
	}

}

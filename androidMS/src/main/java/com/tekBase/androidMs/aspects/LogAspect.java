package com.tekBase.androidMs.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {
	
	@Autowired
	@Qualifier("logger")
    Logger logger;

	@Around("@annotation(com.tekBase.androidMs.config.ServiceExecutionTime)")
	public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
	    long start = System.currentTimeMillis();
	 
	    Object proceed = joinPoint.proceed();
	 
	    long executionTime = System.currentTimeMillis() - start;
	 
	    logger.info("##ExecutionStats : "+ joinPoint.getSignature().getName() + " executed in " + executionTime + "ms");
	    return proceed;
	}
	
	
	
}

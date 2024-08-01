package com.akashxdev.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ExchangeAop {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ExchangeAop.class);
	
	@AfterThrowing("execution(* com.akashxdev.service.JWTService.*(..)) || execution(* com.akashxdev.service.MyUserDetailsService.*(..)) || execution(* com.akashxdev.service.StockExchangeService.*(..))")
	public void logMethodCrash(ProceedingJoinPoint jp) throws Throwable {
		jp.proceed();
		LOGGER.info("Wrong inputs " + jp.getSignature().getName());
	}
	
}
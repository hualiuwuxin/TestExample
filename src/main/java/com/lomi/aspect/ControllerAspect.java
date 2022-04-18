package com.lomi.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ControllerAspect {

    private static final Logger logger = LoggerFactory.getLogger(ControllerAspect.class);


    private static final String CUT = "execution(* com.lomi.controller..*.*(..))";

    
    
    @Around(CUT)
	public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
		Object result = null;
		try {
			Long start = System.currentTimeMillis();
			result = joinPoint.proceed();

			Long end = System.currentTimeMillis();
			logger.warn(joinPoint.getSignature().getName() +"--------------"+ "耗时:{}" ,(end - start) );
		} catch (Throwable e) {
			throw e;
		}
		
		return result;
	}

  

}

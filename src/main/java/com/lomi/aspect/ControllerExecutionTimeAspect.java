package com.lomi.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 打印 控制层每个方法执行了多少时间
 * @author ZHANGYUKUN
 *
 */
@Aspect
@Component
@Order(1000)
public class ControllerExecutionTimeAspect{

    private static final Logger logger = LoggerFactory.getLogger(ControllerExecutionTimeAspect.class);

    /**
     * 切控制层的所有方法
     */
    private static final String CUT = "execution(* com.lomi.controller..*.*(..))";

    
    @Around(CUT)
	public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
		Object result = null;
		try {
			String path = joinPoint.getSignature().getDeclaringType().getName() +"." +joinPoint.getSignature().getName();
			Long start = System.currentTimeMillis();
			result = joinPoint.proceed();
			Long end = System.currentTimeMillis();
			logger.warn("---------------------方法:{},耗时:{}--------------------" ,path,(end - start) );
		} catch (Throwable e) {
			throw e;
		}
		
		return result;
	}

}

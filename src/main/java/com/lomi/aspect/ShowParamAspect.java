package com.lomi.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

/**
 * 如果某些方法标记了@ShowParam，那么就会打印参数
 * @author ZHANGYUKUN
 *
 */
@Aspect
@Component
@Order(1900)
public class ShowParamAspect{
    private static final Logger logger = LoggerFactory.getLogger(ShowParamAspect.class);
    
    /**
     * @ShowParam 的方法
     */
    private static final String CUT = "@annotation(com.lomi.annotation.ShowParam)";
    
    
    @Around(CUT)
	public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
		Object result = null;
		try {
			String path = joinPoint.getSignature().getDeclaringType().getName() +"." +joinPoint.getSignature().getName();
			logger.warn("---------------------方法:{},参数:{}--------------------",path,JSONObject.toJSONString( joinPoint.getArgs() ));
			result = joinPoint.proceed();
    	} catch (Throwable e) {
			throw e;
		}
		return result;
	}


}

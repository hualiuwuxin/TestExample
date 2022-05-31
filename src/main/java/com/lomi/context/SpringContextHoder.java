package com.lomi.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * spring容器的持有者
 * @author ZHANGYUKUN
 *
 */
@Component
public class SpringContextHoder implements ApplicationContextAware  {

	private static ApplicationContext context;

	public static ApplicationContext getContext() {
		return context;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringContextHoder.context = applicationContext;
	}

	
}

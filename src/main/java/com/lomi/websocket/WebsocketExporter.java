package com.lomi.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * 
 * @author ZHANGYUKUN
 *
 */
@Configuration
public class WebsocketExporter {
	
	/**
	 * 必须有这个 @ServerEndpoint 才生效,ServerEndpointExporter 会把检查并注册 @ServerEndpoint (如果不是内嵌的tomcat，是外置的tomcat，不需要这个)
	 * @return
	 */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}
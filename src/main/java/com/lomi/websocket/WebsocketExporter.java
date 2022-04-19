package com.lomi.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * 必须有这个 @ServerEndpoint 才生效
 * @author ZHANGYUKUN
 *
 */
@Configuration
public class WebsocketExporter {

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}
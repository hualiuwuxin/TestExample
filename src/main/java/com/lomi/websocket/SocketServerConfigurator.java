package com.lomi.websocket;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * websocketserver 鉴权
 */
@Component
public class SocketServerConfigurator extends ServerEndpointConfig.Configurator {
	private final Logger logger = LoggerFactory.getLogger(SocketServerConfigurator.class);

	/**
	 * token鉴权认证
	 *
	 * @param originHeaderValue
	 * @return
	 */
	@Override
	public boolean checkOrigin(String originHeaderValue) {
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = servletRequestAttributes.getRequest();
		String url = request.getServletPath();
		
		//请求url中可以带参数
		logger.warn( "请求url" +  url );
		
		//用自定义协议传达参数
		String token = request.getHeader("Sec-WebSocket-Protocol");
		logger.warn("Sec-WebSocket-Protocol是:{}" + token);
		servletRequestAttributes.getResponse().setHeader("Sec-WebSocket-Protocol",   token );
		
		return true;
	}

	/**
	 * Modify the WebSocket handshake response 修改websocket 返回值
	 *
	 * @param sec
	 * @param request
	 * @param response
	 */
	@Override
	public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
		super.modifyHandshake(sec, request, response);

	}
}

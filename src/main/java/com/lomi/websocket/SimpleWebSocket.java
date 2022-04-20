package com.lomi.websocket;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;



/**
 * 每个连接会创建一个新的 ServerEndpoint 实例，所以成员变量 是当前ServerEndpoint私有的
 * websocket可以 带有用户参数的地方只有 自定义协议 和  PathParam，当然还有message 体
 * 
 * 
 * @author ZHANGYUKUN
 *
 *
 *
 *
 */
@ServerEndpoint(value="/websocket/{path}",configurator = SocketServerConfigurator.class)
@Component
public class SimpleWebSocket {
	private static final Logger logger = LoggerFactory.getLogger(SimpleWebSocket.class);
	
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;
     
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    private static CopyOnWriteArraySet<SimpleWebSocket> webSocketSet = new CopyOnWriteArraySet<SimpleWebSocket>();
     
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    
    private static int i = 0;
     
    /**
     * 连接建立成功调用的方法
     * @param session  可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(Session session){
        this.session = session;
        webSocketSet.add(this);     //加入set中
        addOnlineCount();           //在线数加1
        logger.warn("有新连接加入！当前在线人数为" + getOnlineCount());
    }
     
    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(){
        webSocketSet.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1    
        logger.warn("有一连接关闭！当前在线人数为" + getOnlineCount());
    }
     
    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     * @throws IOException 
     */
    @OnMessage
    public void onMessage(@PathParam("path") String path, String message, Session session) throws IOException {
    	i++;
    	logger.warn("来自客户端" + session.getId() + "的path:" + path+i);
        logger.warn("来自客户端" + session.getId() + "的消息:" + message+i);
        sendMessage(message+i);
    }
     
    /**
     * 发生错误时调用
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error){
        logger.warn("发生错误");
        error.printStackTrace();
    }
     
    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException{
        this.session.getBasicRemote().sendText(message);
        //this.session.getAsyncRemote().sendText(message);
    }
 
    public static synchronized int getOnlineCount() {
        return onlineCount;
    }
 
    public static synchronized void addOnlineCount() {
        SimpleWebSocket.onlineCount++;
    }
     
    public static synchronized void subOnlineCount() {
        SimpleWebSocket.onlineCount--;
    }
}

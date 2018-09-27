package com.candy.controller;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.socket.WebSocketSession;

import com.candy.service.SshService;

@ServerEndpoint("/websocket/{id}")
@Controller
public class SshController {
	
	private static final Logger LOG = LoggerFactory.getLogger(SshController.class);

	private static SshService sshService;
	
	public SshController()
	{
		
	}
	
    @Autowired
    public SshController(SshService sshService) {
    	if (SshController.sshService == null)
    	{
    		SshController.sshService = sshService;
    	}
    }
	/**
	 * 连接建立成功调用的方法
	 */
	@OnOpen
	public void onOpen(@PathParam("id") String id, Session session) {
		sshService.createClient(id, session);
		LOG.info("create a client. id = {}", id);
	}
	 
    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(@PathParam("id") String id) {
    	sshService.closeClient(id);
    	LOG.info("close a client. id = {}", id);
    }
	 
	/**
	 * 收到客户端消息后调用的方法
	 *
	 * @param message
	 *            客户端发送过来的消息
	 */
	@OnMessage
	public void onMessage(@PathParam("id") String id, String message) {
		sshService.sendMessage(id, message);
	}
//	 
//	    /**
//	     *
//	     * @param session
//	     * @param error
//	     */
//	    @OnError
//	    public void onError(Session session, Throwable error) {
//	        log.error("发生错误");
//	        error.printStackTrace();
//	    }
//	 
//	 
//	    public void sendMessage(String message) throws IOException {
//	        this.session.getBasicRemote().sendText(message);
//	    }
//	 
//	    /**
//	     * 发送信息给指定ID用户，如果用户不在线则返回不在线信息给自己
//	     * @param message
//	     * @param sendUserId
//	     * @throws IOException
//	     */
//	    public void sendtoUser(String message,String sendUserId) throws IOException {
//	        if (webSocketSet.get(sendUserId) != null) {
//	            if(!id.equals(sendUserId))
//	                webSocketSet.get(sendUserId).sendMessage( "用户" + id + "发来消息：" + " <br/> " + message);
//	            else
//	                webSocketSet.get(sendUserId).sendMessage(message);
//	        } else {
//	            //如果用户不在线则返回不在线信息给自己
//	            sendtoUser("当前用户不在线",id);
//	        }
//	    }
//	 
//	    /**
//	     * 发送信息给所有人
//	     * @param message
//	     * @throws IOException
//	     */
//	    public void sendtoAll(String message) throws IOException {
//	        for (String key : webSocketSet.keySet()) {
//	            try {
//	                webSocketSet.get(key).sendMessage(message);
//	            } catch (IOException e) {
//	                e.printStackTrace();
//	            }
//	        }
//	    }
//	 
//	 
//	    public static synchronized int getOnlineCount() {
//	        return onlineCount;
//	    }
//	 
//	    public static synchronized void addOnlineCount() {
//	        WebSocketServer.onlineCount++;
//	    }
//	 
//	    public static synchronized void subOnlineCount() {
//	        WebSocketServer.onlineCount--;
//	    }
//	}

}

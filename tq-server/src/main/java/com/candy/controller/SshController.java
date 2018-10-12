package com.candy.controller;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.candy.service.SshService;
import com.candy.utils.TqLog;

@ServerEndpoint("/websocket/{id}")
@Controller
public class SshController {

    private static SshService sshService;

    public SshController() {

    }

    @Autowired
    public SshController(SshService sshService) {
        if (SshController.sshService == null) {
            SshController.sshService = sshService;
        }
    }

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(@PathParam("id") String id, Session session) {
        sshService.createClient(id, session);
        TqLog.getDailyLog().info("create ssh client. id = {}", id);
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(@PathParam("id") String id) {
        sshService.closeClient(id);
        TqLog.getDailyLog().info("close ssh client. id = {}", id);
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

}

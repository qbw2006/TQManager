package com.candy.service.manage;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.candy.config.redis.RedisConfigResolver;
import com.candy.config.redis.RedisServer;
import com.google.common.base.Charsets;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

@Component
public class RedisReceiver {
	
	private static final Logger LOG = LoggerFactory.getLogger(RedisReceiver.class);
	
	@Autowired
	private RedisConfigResolver rc;
	
	public void receiveMessage(RedisTask rt)
	{
		LOG.info("receive a operaton task = {} ", rt.toString());
		RedisServer server = rc.getServer(rt.getId());
		JSch js = new JSch();
		try {
			
			//创建session
			Session s = js.getSession(server.getServerUsername(), server.getServerHost());
			s.setPassword(server.getServerPassword());
			s.setConfig("StrictHostKeyChecking", "no");
			s.connect();
			
			LOG.info("create a session . server info = {}", server.getServerInfo());
			
			//生成命令
			String command = rt.getOpertion() == 0 ? server.getStopCommand():server.getStartCommand();
			
			LOG.info("command = {}", command);
			
			//执行
			String result = execCommandByJSch(s, command, Charsets.UTF_8);
			
			LOG.info("exec result = {}", result);
			
		} catch (JSchException e) {
			
			LOG.error("connect host fail, server info = {}", server.getServerInfo(), e);
			
		} catch (IOException e) {
			LOG.error("io is error", e);
		}
	}
	
	public static String execCommandByJSch(Session session, String command, Charset encode) throws IOException, JSchException {
		ChannelExec channelExec = (ChannelExec) session.openChannel("exec");
		InputStream in = channelExec.getInputStream();
		channelExec.setCommand(command);
		channelExec.setErrStream(System.err);
		channelExec.connect();
		String result = IOUtils.toString(in, encode);
		channelExec.disconnect();
 
		return result;
	}

}

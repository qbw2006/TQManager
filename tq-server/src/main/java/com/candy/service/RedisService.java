package com.candy.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.candy.config.redis.RedisConfig;
import com.candy.config.redis.RedisConfigResolver;
import com.candy.service.patrol.RedisResult;
import com.candy.utils.TqLog;
import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

@Service
public class RedisService {
	
	private static final String channelName = "tqManager";
	
	@Autowired
	private RedisConfigResolver rc;
	
	@Autowired
	private StringRedisTemplate redisTemplate; 
	
	public List<RedisResult> healthInfo()
	{
		List<RedisResult> res = Lists.newArrayList();
		
		List<String> keys = Lists.newArrayList();
		rc.getServerMap().forEach((k, v)->keys.add(k));
		List<String> resString = redisTemplate.opsForValue().multiGet(keys);
		
		resString.forEach((k) -> res.add(JSONObject.parseObject(k, RedisResult.class)));
		
		return res;
	}
	
	public void pubRedisTask(OperationTask ot)
	{
		redisTemplate.convertAndSend(channelName, JSON.toJSONString(ot));
		TqLog.getDailyLog().info("pub a task [success]. task = {}", ot.toString());
	}
	
	
	public void receiveMessage(String message)
	{
		OperationTask rt = JSON.parseObject(message, OperationTask.class);
		
		TqLog.getDailyLog().info("receive a task from redis = {} ", message);
		RedisConfig server = rc.getServer(rt.getId());
		JSch js = new JSch();
		try {
			
			//创建session
			Session s = js.getSession(server.getServerUsername(), server.getRedisHost());
			s.setPassword(server.getServerPassword());
			s.setConfig("StrictHostKeyChecking", "no");
			s.connect();
			
			TqLog.getDailyLog().info("create a session. server info = {}", server.getServerInfo());
			
			//生成命令
			String command = rt.getOpertion() == 0 ? server.getStopCommand():server.getStartCommand();
			
			TqLog.getDailyLog().info("command = {}", command);
			
			//执行
			String result = execCommandByJSch(s, command, Charsets.UTF_8);
			
			TqLog.getDailyLog().info("exec result = {}", result);
			
		} catch (JSchException e) {
			
			TqLog.getDailyLog().error("jsch [fail], server info = {}", server.getServerInfo(), e);
			
		} catch (IOException e) {
			
			TqLog.getDailyLog().error("io is error", e);
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

package com.candy.config.redis;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.annotation.JSONField;

public class RedisServer 
{
	@JSONField(name="name")
	private String name;
	
	@JSONField(name="redis-host")
	private String redisHost;
	
	@JSONField(name="redis-port")
	private int redisPort = 6379;
	
	@JSONField(name="redis-password")
	private String redisPassword;
	
	@JSONField(name="redis-mode")
	private int redisMode = 0;
	
	@JSONField(name="server-host")
	private String serverHost;
	
	@JSONField(name="server-username")
	private String serverUsername;
	
	@JSONField(name="server-password")
	private String serverPassword;
	
	
	public boolean isCorrect()
	{
		if (StringUtils.isEmpty(name) || StringUtils.isEmpty(redisHost) ||
				redisPort < 0  || redisMode < 0 ||
				StringUtils.isEmpty(serverHost) || StringUtils.isEmpty(serverUsername) || StringUtils.isEmpty(serverPassword))
		{
			return false;
		}
		return true;
	}
	
	public int getRedisMode() {
		return redisMode;
	}
	public void setRedisMode(int redisMode) {
		this.redisMode = redisMode;
	}
	public String getServerHost() {
		return serverHost;
	}
	public void setServerHost(String serverHost) {
		this.serverHost = serverHost;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRedisHost() {
		return redisHost;
	}
	public void setRedisHost(String redisHost) {
		this.redisHost = redisHost;
	}
	public int getRedisPort() {
		return redisPort;
	}
	public void setRedisPort(int redisPort) {
		this.redisPort = redisPort;
	}
	public String getRedisPassword() {
		return redisPassword;
	}
	public void setRedisPassword(String redisPassword) {
		this.redisPassword = redisPassword;
	}
	public String getServerUsername() {
		return serverUsername;
	}
	public void setServerUsername(String serverUsername) {
		this.serverUsername = serverUsername;
	}
	public String getServerPassword() {
		return serverPassword;
	}
	public void setServerPassword(String serverPassword) {
		this.serverPassword = serverPassword;
	}

	@Override
	public String toString() {
		return "RedisServer [name=" + name + ", redisHost=" + redisHost + ", redisPort=" + redisPort
				+ ", redisPassword=" + redisPassword + ", redisMode=" + redisMode + ", serverHost=" + serverHost
				+ ", serverUsername=" + serverUsername + ", serverPassword=" + serverPassword + "]";
	}
	
}

package com.candy.config.redis;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.annotation.JSONField;

public class RedisConfig 
{
	
	@JSONField(name="name")
	private String name;
	
	@JSONField(name="redis-host")
	private String redisHost;
	
	@JSONField(name="redis-port")
	private int redisPort = 6379;
	
	@JSONField(name="redis-password")
	private String redisPassword;
	
	@JSONField(name="server-username")
	private String serverUsername;
	
	@JSONField(name="server-password")
	private String serverPassword;
	
	@JSONField(name="redis-bin-path")
	private String redisBinPath;
	
	@JSONField(name="redis-config-path")
	private String redisConfigPath;
	
	
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

	public String getRedisBinPath() {
		return redisBinPath;
	}

	public void setRedisBinPath(String redisBinPath) {
		this.redisBinPath = redisBinPath;
	}

	public String getRedisConfigPath() {
		return redisConfigPath;
	}

	public void setRedisConfigPath(String redisConfigPath) {
		this.redisConfigPath = redisConfigPath;
	}
	
	@Override
	public String toString() {
		return "RedisConfig [name=" + name + ", redisHost=" + redisHost + ", redisPort=" + redisPort
				+ ", redisPassword=" + redisPassword + ", serverUsername=" + serverUsername + ", serverPassword="
				+ serverPassword + ", redisBinPath=" + redisBinPath + ", redisConfigPath=" + redisConfigPath + "]";
	}
	public String getStartCommand()
	{
		return redisBinPath + " " + redisConfigPath;
	}
	
	public String getStopCommand()
	{
		return "pkill -f " + getId();
	}
	

	public String getId()
	{
		return redisHost + ":" + redisPort;
	}

	public String getServerInfo()
	{
		return "ServerInfo [serverHost=" + redisHost
				+ ", serverUsername=" + serverUsername + ", serverPassword=" + serverPassword + "]";
	}

	public boolean isCorrect()
	{
		if (StringUtils.isEmpty(name) || StringUtils.isEmpty(redisHost) ||
				redisPort < 0
				|| StringUtils.isEmpty(serverUsername) || StringUtils.isEmpty(serverPassword))
		{
			return false;
		}
		return true;
	}
}

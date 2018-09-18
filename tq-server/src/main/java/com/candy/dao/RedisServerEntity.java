package com.candy.dao;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;

public class RedisServerEntity 
{
	@NotBlank
	private String name;
	
	@NotBlank
	private String redisHost;
	
	private int redisPort = 6379;
	
	@NotBlank
	private String redisPassword;
	
	@NotBlank
	private String serverUsername;
	
	@NotBlank
	private String serverPassword;
	
	@NotBlank
	private String redisBinPath;
	
	@NotBlank
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

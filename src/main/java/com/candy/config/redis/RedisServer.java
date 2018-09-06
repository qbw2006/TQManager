package com.candy.config.redis;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.annotation.JSONField;
import com.google.common.collect.Sets;

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
	private List<String> redisMode;
	
	@JSONField(name="server-host")
	private String serverHost;
	
	@JSONField(name="server-username")
	private String serverUsername;
	
	@JSONField(name="server-password")
	private String serverPassword;
	
	@JSONField(name="redis-bin-path")
	private String redisBinPath;
	
	@JSONField(name="redis-config-path")
	private String redisConfigPath;
	
	//根据redis的部署模式,concernKey中保存此redis需要关心的配置
	private final Set<String> concernKey = Sets.newHashSet();
	
	
	public void addConcernKey(Collection<String> key) {
		 concernKey.addAll(key);
	}


	public Set<String> getConcernKey() {
		return Sets.newHashSet(concernKey);
	}

	public boolean isCorrect()
	{
		if (StringUtils.isEmpty(name) || StringUtils.isEmpty(redisHost) ||
				redisPort < 0  || CollectionUtils.isEmpty(redisMode) ||
				StringUtils.isEmpty(serverHost) || StringUtils.isEmpty(serverUsername) || StringUtils.isEmpty(serverPassword))
		{
			return false;
		}
		return true;
	}
	
	public List<String> getRedisMode() {
		return redisMode;
	}
	public void setRedisMode(List<String> redisMode) {
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
		return "RedisServer [name=" + name + ", redisHost=" + redisHost + ", redisPort=" + redisPort
				+ ", redisPassword=" + redisPassword + ", redisMode=" + redisMode + ", serverHost=" + serverHost
				+ ", serverUsername=" + serverUsername + ", serverPassword=" + serverPassword + ", redisBinPath="
				+ redisBinPath + ", redisConfigPath=" + redisConfigPath + ", concernKey=" + concernKey + "]";
	}


	public String getStartCommand()
	{
		return redisBinPath + " " + redisConfigPath;
	}
	
	public String getStopCommand()
	{
		return "pkill -9 " + getStartCommand();
	}
	

	public String getId()
	{
		return redisHost + ":" + redisPort;
	}

	public String getServerInfo()
	{
		return "ServerInfo [serverHost=" + serverHost
				+ ", serverUsername=" + serverUsername + ", serverPassword=" + serverPassword + "]";
	}
}

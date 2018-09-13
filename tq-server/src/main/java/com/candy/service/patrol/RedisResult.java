package com.candy.service.patrol;

import java.util.Date;
import java.util.Map;

import com.alibaba.fastjson.annotation.JSONField;
import com.google.common.collect.Maps;

public class RedisResult {

	private String id;
	private String name;
	private String redisHost;
	private int redisPort;
	private String redisMode;
	@JSONField(name = "isAlive")
	private boolean isAlive = false;
	
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date date;
	
	private Map<String, String> concern = Maps.newHashMap();
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getRedisMode() {
		return redisMode;
	}
	public void setRedisMode(String redisMode) {
		this.redisMode = redisMode;
	}
	public boolean isAlive() {
		return isAlive;
	}
	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}
	public Map<String, String> getConcern() {
		return concern;
	}
	public void setConcern(Map<String, String> concern) {
		this.concern = concern;
	}
	
	public void addConcern(String key, String value) {
		this.concern.put(key, value);
	}
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "RedisResult [id=" + id + ", name=" + name + ", redisHost=" + redisHost + ", redisPort=" + redisPort
				+ ", redisMode=" + redisMode + ", isAlive=" + isAlive + ", date=" + date + ", concern=" + concern + "]";
	}
	
}

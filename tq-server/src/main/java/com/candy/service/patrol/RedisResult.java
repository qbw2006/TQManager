package com.candy.service.patrol;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.assertj.core.util.Lists;

import com.alibaba.fastjson.annotation.JSONField;
import com.google.common.collect.Maps;

public class RedisResult {

	private String id;
	private String name;
	private String redisHost;
	private int redisPort;
	
	@JSONField(name="isAlive")
	private boolean isAlive = false;
	
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date date;
	
	private List<Map<Object, Object>> info = Lists.newArrayList();
	
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
	public boolean isAlive() {
		return isAlive;
	}
	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}
	
	public void addAttr(Object k, Object v)
	{
		Map<Object, Object> m = Maps.newHashMap();
		m.put("key", k);
		m.put("value", v);
		info.add(m);
	}
	
	
	public List<Map<Object, Object>> getInfo() {
		return info;
	}
	public void setInfo(List<Map<Object, Object>> info) {
		this.info = info;
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
				+ ", isAlive=" + isAlive + ", date=" + date + ", info=" + info + "]";
	}
	
}

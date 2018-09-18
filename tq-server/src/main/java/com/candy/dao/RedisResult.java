package com.candy.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;


import com.alibaba.fastjson.annotation.JSONField;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class RedisResult extends RedisServerEntity{

	private String id;
	
	@JSONField(name="isAlive")
	private boolean isAlive = false;
	
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date date;
	
	private List<Map<Object, Object>> info = Lists.newArrayList();
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = super.getId();
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
	
}

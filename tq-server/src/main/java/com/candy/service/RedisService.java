package com.candy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.candy.config.redis.RedisConfigResolver;
import com.candy.service.manage.OperationTask;
import com.candy.service.patrol.RedisResult;
import com.candy.utils.TqLog;
import com.google.common.collect.Lists;

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
		redisTemplate.convertAndSend(channelName, ot);
		TqLog.getDailyLog().info("pub a task [success]. task = {}", ot.toString());
	}
}

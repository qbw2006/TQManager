package com.candy.service.patrol;

import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.candy.config.redis.RedisConfigResolver;
import com.candy.config.redis.RedisConfig;
import com.google.common.base.Joiner;
import com.google.common.collect.Maps;

import redis.clients.jedis.Jedis;

@Service
public class RedisPatroller 
{
	private static final Logger LOG = LoggerFactory.getLogger(RedisPatroller.class);
	
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	@Autowired
	private RedisConfigResolver rc;
	
	private Map<String, JedisConnection> connections = Maps.newHashMap();
	
	@PostConstruct
	public void init()
	{
		for (RedisConfig rs : rc.getServerMap().values())
		{
			Jedis j = new Jedis(rs.getRedisHost(), rs.getRedisPort());
			if (!StringUtils.isEmpty(rs.getRedisPassword()))
			{
				j.auth(rs.getRedisPassword());
			}
			
			JedisConnection f = new JedisConnection(j);
			connections.put(rs.getId(), f);
		}
		
		LOG.info("redis client initial [successfully]");
	}
	
	@Scheduled(cron="${redis.schedule}") 
	public void patrol()
	{
		Map<String, RedisResult> result = checkRedis();
		
		LOG.debug("check result = {}", result);
		
		result.forEach((k, v)->redisTemplate.opsForValue().set(k, JSON.toJSONString(v)));
	}

	
	private Map<String, RedisResult> checkRedis()
	{
		Map<String, RedisResult> res = Maps.newHashMap();
		
		for (Entry<String, JedisConnection> en : connections.entrySet())
		{
			String id = en.getKey();
			JedisConnection  j = en.getValue();
			
			RedisResult rr = new RedisResult();
			//赋值
			rr.setId(id);
			rr.setName(rc.getServer(id).getName());
			rr.setRedisHost(rc.getServer(id).getRedisHost());
			rr.setRedisPort(rc.getServer(id).getRedisPort());
			//返回格式是,分隔的字符串
			rr.setRedisMode(Joiner.on(",").join(rc.getServer(id).getRedisMode()));
			rr.setDate(new Date());
			
			try 
			{
				Properties p = j.info();
				Set<String> concern = rc.getServer(id).getConcernKey();
				concern.forEach(k->rr.addConcern(k, p.getProperty(k)));
				
				rr.setAlive(true);
				
			} catch (Exception e) {
				LOG.error("redis id = {} is disconnect.", id);
			}
			
			res.put(id, rr);
		}
		return res;
	}
	
}

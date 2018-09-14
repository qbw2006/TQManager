package com.candy.service.patrol;

import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.candy.config.redis.RedisConfig;
import com.candy.config.redis.RedisConfigResolver;
import com.candy.service.TQRedisClient;
import com.candy.utils.TqLog;
import com.google.common.base.Joiner;
import com.google.common.collect.Maps;

import redis.clients.jedis.JedisPoolConfig;

@Service
public class RedisPatroller 
{
	
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	@Autowired
	private RedisConfigResolver rc;
	
	private Map<String, TQRedisClient> connections = Maps.newHashMap();
	
	@PostConstruct
	public void init()
	{
		for (RedisConfig rs : rc.getServerMap().values())
		{
			TQRedisClient tqRedis = null;
			try
			{
				tqRedis = createTQRedisClient(rs);
			}
			catch(Exception e)
			{
				TqLog.getErrorLog().warn("create tqredis client. info = {}", rs);
			}
			connections.put(rs.getId(), tqRedis);
		}
		
		TqLog.getDailyLog().info("RedisPatroller start [successfully]");
	}
	
	@Scheduled(cron="${redis.schedule}") 
	public void patrol()
	{
		Map<String, RedisResult> result = checkRedis();
		
		TqLog.getDailyLog().debug("check result = {}", JSON.toJSONString(result));
		
		result.forEach((k, v)->redisTemplate.opsForValue().set(k, JSON.toJSONString(v)));
	}
	
	/**
	 * 因为JedisConnection断线不会重连,所以要使用带池的RedisConnectionFactory。
	 * 这样redis宕机后RedisConnectionFactory便会重连
	 * @param rs
	 * @return
	 */
	private TQRedisClient createTQRedisClient(RedisConfig rs)
	{
		JedisConnectionFactory jcf = new JedisConnectionFactory();
		jcf.setHostName(rs.getRedisHost());
		jcf.setPort(rs.getRedisPort());
		jcf.setClientName(rs.getId());
		
		if (!StringUtils.isEmpty(rs.getRedisPassword()))
		{
			jcf.setPassword(rs.getRedisPassword());
		}
		
		JedisPoolConfig jpc = new JedisPoolConfig();
		//设置最大等待链接的时间
		//bug场景：redis在线--》掉线--》在线，如果不设置MaxWaitMillis会出现卡死。
		jpc.setMaxWaitMillis(3000);
		
		jcf.setPoolConfig(jpc);
		jcf.afterPropertiesSet();
		
		return new TQRedisClient(new StringRedisTemplate(jcf));
	}

	
	private Map<String, RedisResult> checkRedis()
	{
		Map<String, RedisResult> res = Maps.newHashMap();
		
		for (Entry<String, TQRedisClient> en : connections.entrySet())
		{
			String id = en.getKey();
			TQRedisClient  tqRedis = en.getValue();
			
			RedisResult rr = new RedisResult();
			//赋值
			rr.setId(id);
			rr.setName(rc.getServer(id).getName());
			rr.setRedisHost(rc.getServer(id).getRedisHost());
			rr.setRedisPort(rc.getServer(id).getRedisPort());
			rr.setDate(new Date());
			
			try 
			{
				Properties p = tqRedis.info();
				p.forEach((k, v)->rr.addAttr(k, v));
				rr.setAlive(true);
				
			} catch (Exception e) {
				TqLog.getErrorLog().warn("redis id = {} is disconnect.", id);
			}
			
			res.put(id, rr);
		}
		return res;
	}
	
}

package com.candy.service;

import java.util.Properties;

import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * 
 * @author Administrator
 *
 */
public class TQRedisClient {

	private final StringRedisTemplate redisTemplate;
	
	public TQRedisClient(StringRedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
	
	public Properties info() {

		return redisTemplate.execute(new RedisCallback<Properties>() {

			public Properties doInRedis(RedisConnection connection) {
				return connection.info();
			}
		}, true);
	}
}

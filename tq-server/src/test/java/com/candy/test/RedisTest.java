package com.candy.test;

import org.junit.Test;
import org.springframework.data.redis.connection.jedis.JedisConnection;

import redis.clients.jedis.Jedis;

public class RedisTest {

	@Test
	public void info()
	{
		Jedis j = new Jedis("10.1.20.108", 6379);
		j.auth("sinosun");
		
		JedisConnection jc = new JedisConnection(j);
		System.out.println(jc.info("sync"));
	}
}

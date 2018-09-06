package com.candy.test;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.data.redis.connection.jedis.JedisConnection;

import redis.clients.jedis.Jedis;

public class MangerTest {

	@Test
	public void test1()
	{
		Jedis j = new Jedis("127.0.0.1", 6379);
		
		JedisConnection f = new JedisConnection(j);
		
		
		System.out.println(f.info());
	}
}

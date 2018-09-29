package com.candy.dao.redis;

import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Component;

import com.candy.dao.IOperationTask;

/**
 * 
 * @author Administrator
 *
 */
@Component
public class TqRedisClient {

	private static final String CHANLE_KEY = "redis-task";
	
	private static final String PREFIX = "tq-";
	
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	public TqRedisClient() {
		
	}
	
	public TqRedisClient(StringRedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
	
	public Properties info() {

		return redisTemplate.execute(new RedisCallback<Properties>() {

			@Override
			public Properties doInRedis(RedisConnection connection) {
				return connection.info();
			}
		}, true);
	}
	
	public void put(String key, Object hashKey, Object value) {
		redisTemplate.opsForHash().put(getKey(key), hashKey, value);
	}
	
	public Map<Object, Object> entries(String key)
	{
		return redisTemplate.opsForHash().entries(getKey(key));
	}
	
	public void delete(String key, Object...hashKeys)
	{
		redisTemplate.opsForHash().delete(getKey(key), hashKeys);
	}
	
	public Object get(String key, Object hashKey)
	{
		return redisTemplate.opsForHash().get(getKey(key), hashKey);
	}
	
	public void set(String key, String value)
	{
		redisTemplate.opsForValue().set(getKey(key), value);
	}
	
	public void convertAndSend(String message)
	{
		redisTemplate.convertAndSend(getKey(CHANLE_KEY), message);
	}
	
    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
            MessageListenerAdapter listenerAdapter) {
 
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter, new PatternTopic(getKey(CHANLE_KEY)));
        return container;
    }
    
    @Bean
    public MessageListenerAdapter listenerAdapter(IOperationTask redisTask) {
        return new MessageListenerAdapter(redisTask, "handleTask");
    }
    
    //使用默认的工厂初始化redis操作模板
    @Bean
    StringRedisTemplate template(RedisConnectionFactory connectionFactory) {
        return new StringRedisTemplate(connectionFactory);
    }
    
    private  static String getKey(String key)
    {
    	return PREFIX + key;
    }
}

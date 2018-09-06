package com.candy.service.manage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import com.candy.service.patrol.RedisPatroller;

@Configuration
public class RedisManager {

	private static final Logger LOG = LoggerFactory.getLogger(RedisPatroller.class);
	
	private static final String channelName = "tqManager";
	
	@Autowired
	private RedisTemplate<String, RedisTask> redisTemplate;
	
	public void pubRedisTask(RedisTask rt)
	{
		redisTemplate.convertAndSend(channelName, rt);
		LOG.info("pub a task. task = []", rt.toString());
	}
	
    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
            MessageListenerAdapter listenerAdapter) {
 
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter, new PatternTopic(channelName));
 
        return container;
    }
    
    @Bean
    public MessageListenerAdapter listenerAdapter(RedisReceiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }
    
}

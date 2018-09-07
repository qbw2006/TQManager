package com.candy.service.manage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Service;

import com.candy.service.patrol.RedisPatroller;

@Service
public class RedisManager {

	private static final Logger LOG = LoggerFactory.getLogger(RedisPatroller.class);
	
	private static final String channelName = "tqManager";
	
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	public void pubRedisTask(OperationTask rt)
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
    
    //使用默认的工厂初始化redis操作模板
    @Bean
    StringRedisTemplate template(RedisConnectionFactory connectionFactory) {
        return new StringRedisTemplate(connectionFactory);
    }
    
}

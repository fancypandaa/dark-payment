package com.bank.payment.config;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
@AllArgsConstructor
public class RedisSubscriberConfig {
    private static final Logger log= LoggerFactory.getLogger(RedisSubscriberConfig.class);

    private final MessageListener messageListener;
    private final RedisConnectionFactory redisConnectionFactory;
    @Bean
    public MessageListenerAdapter messageListenerAdapter(){
        return new MessageListenerAdapter(messageListener);
    }
    @Bean
    public RedisMessageListenerContainer redisContainer(ChannelTopic channelTopic){
        RedisMessageListenerContainer container =new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        container.addMessageListener(messageListener,channelTopic);
        log.info(messageListener+" XXX "+channelTopic);
        return container;
    }
}

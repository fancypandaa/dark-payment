package com.bank.payment.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

@Configuration
public class RedisConfiguration {

    @Value("${redis.pub_sub.topic:channel-events}")
    private String topic;
    @Bean
    public ChannelTopic channelTopic(){
        return new ChannelTopic(topic);
    }
    @Bean(name = "pub_subRedisTemplate")
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory connectionFactory){
        RedisTemplate<String,Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }

}

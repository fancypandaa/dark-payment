package com.bank.payment.auctions;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AuctionsEventListener implements MessageListener {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    @Qualifier("pub_subRedisTemplate")
    private RedisTemplate<String,Object> redisTemplate;


    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            AuctionModel auctionModel =objectMapper.readValue(message.getBody(),AuctionModel.class);
            redisTemplate.opsForValue().set(auctionModel.getAuctionId(),auctionModel);
            System.out.println(message.getBody()+"  "+message.getChannel());
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }
}

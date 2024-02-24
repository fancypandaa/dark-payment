package com.bank.payment.auctions.subscriber;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.*;
import java.io.IOException;

@Service
public class AuctionsEventListener implements MessageListener {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    @Qualifier("pub_subRedisTemplate")
    private RedisTemplate<String,Object> redisTemplate;

    private LinkedList<AuctionModel> ll= new LinkedList();
    @Autowired
    AuctionBuilder auctionBuilder;
    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            AuctionModel auctionModel =objectMapper.readValue(message.getBody(),AuctionModel.class);
            redisTemplate.opsForValue().set(auctionModel.getAuctionId(),auctionModel);
            auctionBuilder.createAuction(auctionModel);
            System.out.println("Message received: " + auctionModel.getCurrentAmount()+ " " + auctionModel.getCurrencyType()
            +" ->> "+ auctionModel.getCurrencyTo()+ " "+ auctionModel.getCreatedAt());
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }
}

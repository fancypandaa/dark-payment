package com.bank.payment.auctions.factory;

import com.bank.payment.auctions.domain.AuctionModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class AuctionsEventListener implements MessageListener {
    private static final Logger log= LoggerFactory.getLogger(AuctionsEventListener.class);
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    @Qualifier("pub_subRedisTemplate")
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    AuctionBuilder auctionBuilder;
    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            AuctionModel auctionModel =objectMapper.readValue(message.getBody(),AuctionModel.class);
            auctionBuilder.createAuction(auctionModel);
            log.info("Message received: " + auctionModel.getCurrentAmount()+ " " + auctionModel.getCurrencyType()
            +" ->> "+ auctionModel.getCurrencyTo()+ " "+ auctionModel.getCreatedAt());

        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }

}

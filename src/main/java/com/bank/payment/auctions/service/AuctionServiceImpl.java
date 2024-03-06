package com.bank.payment.auctions.service;

import com.bank.payment.auctions.domain.AuctionForm;
import com.bank.payment.auctions.domain.AuctionModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.UUID;

@Service
public class AuctionServiceImpl implements AuctionService{
    private static final Logger log= LoggerFactory.getLogger(AuctionServiceImpl.class);
    @Autowired
    @Qualifier("pub_subRedisTemplate")
    private RedisTemplate<String,Object> redisTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ChannelTopic channelTopic;

    @Override
    public Long publish(AuctionModel auctionModel) {
        try{
            UUID uuid= UUID.randomUUID();
            auctionModel.setAuctionId(uuid.toString());
            auctionModel.setCreatedAt((Instant.now()).toString());
            return redisTemplate.convertAndSend(channelTopic.getTopic(),auctionModel);
        }
        catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public void putAuctionForm(String auctionId,AuctionForm auctionForm) {
        try {
            redisTemplate.opsForHash().put(auctionId,AuctionForm.class.getFields(),auctionForm);
        }
        catch (RuntimeException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public AuctionForm getAuctionForm(String auctionId) {
        try {
            Object o= redisTemplate.opsForHash().get(auctionId,AuctionForm.class.getFields());
            if(o == null) return null;
            AuctionForm auctionForm =objectMapper.convertValue(o,AuctionForm.class);
            return auctionForm;
        }
        catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public void deleteAuctionForm(String auctionId) {
        redisTemplate.opsForHash().delete(auctionId);
    }


}

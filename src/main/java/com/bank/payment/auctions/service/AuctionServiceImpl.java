package com.bank.payment.auctions.service;

import com.bank.payment.auctions.domain.AuctionModel;
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
    private ChannelTopic channelTopic;

    @Override
    public Long publish(AuctionModel auctionModel) {
        UUID uuid= UUID.randomUUID();
        auctionModel.setAuctionId(uuid.toString());
        auctionModel.setCreatedAt((Instant.now()).toString());
        return redisTemplate.convertAndSend(channelTopic.getTopic(),auctionModel);
    }
}

package com.bank.payment.auctions.subscriber;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Component
public class AuctionBuilder {
    private static final Logger log= LoggerFactory.getLogger(AuctionBuilder.class);

    @Autowired
    AuctionFactory auctionFactory;
    private static LinkedList<AuctionModel> ll= new LinkedList();

    public void createAuction(AuctionModel auctionModel){
        ll.add(auctionModel);
        auctionFactory.getOrCreateAuction(auctionModel);
        log.info("Auction List Size::" ,ll.size());
    }
    private AuctionModel findInList(String auctionId){
        for(int i=0;i<ll.size();i++){
            if(ll.get(i).getAuctionId().equals(auctionId)){
                return ll.get(i);
            }
        }
        return null;
//        log.warn("convert List into redis soon",null);
    }
    public void closeAuction(String auctionId,Optional<String> voterId){
        AuctionModel auctionModel=findInList(auctionId);
        if(auctionModel == null) return;
        this.ll.remove(auctionModel);
        if(voterId.isPresent()){
            auctionFactory.closeAuction(auctionModel.getAuctionId(),voterId.get());
        }
        else{
            auctionFactory.removeAuction(auctionModel.getAuctionId());
        }
     }
    public void extendAuction(String auctionId){
        auctionFactory.extendAuction(auctionId);
     }
    public void addVote(String auctionId,String voterId, BigDecimal offer){
        log.info("addVote ",auctionId,voterId);
        auctionFactory.addVote(auctionId,voterId,offer);
     }

}

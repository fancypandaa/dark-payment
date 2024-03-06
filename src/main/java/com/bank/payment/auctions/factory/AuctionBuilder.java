package com.bank.payment.auctions.factory;

import com.bank.payment.auctions.domain.AuctionForm;
import com.bank.payment.auctions.domain.AuctionModel;
import org.springframework.beans.factory.annotation.Autowired;
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
    public AuctionForm createAuction(AuctionModel auctionModel){
        AuctionForm auctionForm=auctionFactory.getOrCreateAuction(auctionModel);
        log.info("auction form"+auctionForm.toString());
        return auctionForm;
    }
    public void closeAuction(String auctionId,Optional<String> voterId){
        AuctionForm auctionForm = auctionFactory.findFormById(auctionId);
        if(auctionForm == null) return;
        if(voterId.isPresent() && auctionForm.getBuyers().get(voterId).equals(voterId)){
            auctionFactory.closeAuction(auctionForm.getAuctionId(),voterId.get());
        }
        else{
            auctionFactory.removeAuction(auctionForm.getAuctionId());
        }
     }
    public void extendAuction(String auctionId){
        auctionFactory.extendAuction(auctionId);
     }
    public void addVote(String auctionId,String voterId, BigDecimal offer) {
        log.info("addVote "+auctionId+" "+voterId);
        auctionFactory.addVote(auctionId,voterId,offer);
     }
}

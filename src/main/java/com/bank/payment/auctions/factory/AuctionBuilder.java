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
    private static LinkedList<AuctionModel> ll= new LinkedList();

    public AuctionForm createAuction(AuctionModel auctionModel){
        ll.add(auctionModel);
        AuctionForm auctionForm=auctionFactory.getOrCreateAuction(auctionModel);
        log.info("Auction List Size:: "+ll.size());
        log.info("auction form"+auctionForm.toString());
        return auctionForm;
    }
    private AuctionModel findInList(String auctionId){
        for(int i=0;i<ll.size();i++){
            if(ll.get(i).getAuctionId().equals(auctionId)){
                return ll.get(i);
            }
        }
        return null;
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

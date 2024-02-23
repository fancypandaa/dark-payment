package com.bank.payment.auctions.subscriber;

import java.math.BigDecimal;
import java.util.*;

public abstract class AuctionBuilder {
    LinkedList<AuctionModel> ll;

    public AuctionBuilder(LinkedList<AuctionModel> ll) {
        this.ll = ll;
    }

    void createAuction(AuctionModel auctionModel){
        this.ll.add(auctionModel);
        AuctionFactory.getAuction(auctionModel);
    }
     void closeAuction(AuctionModel auctionModel,Optional<String> voterId){
        this.ll.remove(auctionModel);
        if(voterId.isPresent()){
            AuctionFactory.closeAuction(auctionModel.getAuctionId(),voterId.get());
        }
        else{
            AuctionFactory.removeAuction(auctionModel.getAuctionId());
        }
     }
     void extendAuction(String auctionId){
        AuctionFactory.extendAuction(auctionId);
     }
     void addVote(String auctionId,String voterId, BigDecimal offer){
        AuctionFactory.addVote(auctionId,voterId,offer);
     }

}

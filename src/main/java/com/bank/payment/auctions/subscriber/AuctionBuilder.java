package com.bank.payment.auctions.subscriber;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public abstract class AuctionBuilder {
    LinkedList<AuctionModel> ll;

    public AuctionBuilder(LinkedList<AuctionModel> ll) {
        this.ll = ll;
    }

    public void createAuction(AuctionModel auctionModel){
        this.ll.add(auctionModel);
        AuctionFactory.getAuction(auctionModel);
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
            AuctionFactory.closeAuction(auctionModel.getAuctionId(),voterId.get());
        }
        else{
            AuctionFactory.removeAuction(auctionModel.getAuctionId());
        }
     }
    public void extendAuction(String auctionId){
        AuctionFactory.extendAuction(auctionId);
     }
    public void addVote(String auctionId,String voterId, BigDecimal offer){
        AuctionFactory.addVote(auctionId,voterId,offer);
     }

}

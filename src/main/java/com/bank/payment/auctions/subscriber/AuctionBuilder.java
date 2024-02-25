package com.bank.payment.auctions.subscriber;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

@Component
public class AuctionBuilder {
    private static LinkedList<AuctionModel> ll= new LinkedList();

    public void createAuction(AuctionModel auctionModel){
        ll.add(auctionModel);
        AuctionFactory.getAuction(auctionModel);
        System.out.println("List Size: "+ll.size());

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
        System.out.println("addVote "+auctionId+" "+voterId+" ");
        AuctionFactory.addVote(auctionId,voterId,offer);
     }

}

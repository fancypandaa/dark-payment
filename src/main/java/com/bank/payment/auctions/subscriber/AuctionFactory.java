package com.bank.payment.auctions.subscriber;

import java.math.BigDecimal;
import java.util.*;
public class AuctionFactory {
    private static Map<String, AuctionForm> auctionList = new HashMap<>();
    public static AuctionForm getAuction(AuctionModel auctionModel){
        AuctionForm result=auctionList.get(auctionModel.getAuctionId());
        if(result == null){
            AuctionForm auctionForm= new AuctionForm();
            auctionForm.setAuctionId(auctionModel.getAuctionId());
            auctionForm.setState("open");
            auctionForm.setOpenAt(new Date());
            auctionForm.setCloseAt(auctionForm.getOpenAt());
            auctionList.put(auctionModel.getAuctionId(),auctionForm);
        }
        return result;
    }
    public static void removeAuction(String auctionId){
        AuctionForm auctionForm = auctionList.get(auctionId);
        if(auctionForm!=null) {
            auctionList.remove(auctionForm);
        }
    }
    public static void extendAuction(String auctionId){
        AuctionForm auctionForm = auctionList.get(auctionId);
        auctionForm.setCost(auctionForm.getCost()+1);
        auctionForm.setCloseAt(auctionForm.getCloseAt());
        auctionForm.setState("extra");
    }
    public static void addVote(String auctionId,String voterId, BigDecimal offer){
        AuctionForm auctionForm = auctionList.get(auctionId);
        auctionForm.setVoted(auctionForm.getVoted()+1);
        auctionForm.getBuyers().put(voterId,offer);
    }
    public static BigDecimal closeAuction(String auctionId,String voterId){
        AuctionForm auctionForm = auctionList.get(auctionId);
        auctionForm.setCloseAt(new Date());
        auctionForm.setCost(auctionForm.getCost()+10);// just now static
        return auctionForm.getBuyers().get(voterId);
    }
}

package com.bank.payment.auctions.subscriber;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;
@Component
public class AuctionFactory {
    private static Map<String, AuctionForm> auctionList = new HashMap<>();
    public static AuctionForm getAuction(AuctionModel auctionModel){
        Optional<AuctionForm> result=Optional.ofNullable(auctionList.get(auctionModel.getAuctionId()));
        if(result.isEmpty()){
            AuctionForm auctionForm= new AuctionForm();
            auctionForm.setAuctionId(auctionModel.getAuctionId());
            auctionForm.setState("open");
            auctionForm.setOpenAt(new Date());
            auctionForm.setCloseAt(auctionForm.getOpenAt());
            auctionList.put(auctionModel.getAuctionId(),auctionForm);
            result= Optional.of(auctionForm);

        }
        System.out.println("MAP SIZE:: "+ auctionList.size() +" "+result.toString());
        return result.get();
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
        auctionForm.addBuyers(voterId,offer);
        System.out.println("xxxx");
        System.out.println(auctionForm.getBuyers().get(voterId)+" <<<<<<<<");
    }
    public static BigDecimal closeAuction(String auctionId,String voterId){
        AuctionForm auctionForm = auctionList.get(auctionId);
        auctionForm.setCloseAt(new Date());
        auctionForm.setCost(auctionForm.getCost()+10);// just now static
        return auctionForm.getBuyers().get(voterId);
    }
}

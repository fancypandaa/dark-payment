package com.bank.payment.auctions.subscriber;

import com.bank.payment.concurrencyFactory.BalanceFactory;
import com.bank.payment.concurrencyFactory.BalanceOptions;
import com.bank.payment.domain.BalanceTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.Instant;

@Component
public class AuctionFactory {
    private static final Logger logger= LoggerFactory.getLogger(AuctionFactory.class);
    private static Map<String, AuctionForm> auctionList = new HashMap<>();
    @Autowired
    BalanceOptions balanceOptions;
    private AuctionForm createNewAuctionForm(AuctionModel auctionModel){
        BalanceFactory balanceFactory=balanceOptions.createBalance(auctionModel.getCurrencyType(),0.17, BalanceTypes.FOREIGN_CURRENCY);
        AuctionForm auctionForm= new AuctionForm();
        auctionForm.setAuctionId(auctionModel.getAuctionId());
        auctionForm.setState("open");
        auctionForm.setOpenAt(Instant.now());
        auctionForm.setCloseAt(auctionForm.getOpenAt().plus(30, ChronoUnit.MINUTES));
        BigDecimal openingAmount =balanceFactory.calculateInitialCurrencyEquivalent(
                BigDecimal.valueOf(auctionModel.getCurrentAmount()),0.0,BalanceTypes.FOREIGN_CURRENCY);
        auctionForm.setOpeningPrice(openingAmount);
        return auctionForm;
    }
    public AuctionForm getOrCreateAuction(AuctionModel auctionModel){
        Optional<AuctionForm> result=Optional.ofNullable(auctionList.get(auctionModel.getAuctionId()));
        if(result.isEmpty()){
            AuctionForm auctionForm=createNewAuctionForm(auctionModel);
            auctionList.put(auctionModel.getAuctionId(),auctionForm);
            result= Optional.of(auctionForm);
        }
        logger.info("MAP SIZE::",auctionList.size());
        logger.info(result.toString());
        return result.get();
    }

    public void removeAuction(String auctionId){
        AuctionForm auctionForm = auctionList.get(auctionId);
        if(auctionForm!=null) {
            auctionList.remove(auctionForm);
        }
    }
    public void extendAuction(String auctionId){
        AuctionForm auctionForm = auctionList.get(auctionId);
        auctionForm.setCost(auctionForm.getCost()+1);
        auctionForm.setCloseAt(auctionForm.getCloseAt().plus(30,ChronoUnit.MINUTES));
        auctionForm.setState("extra");
    }
    public void addVote(String auctionId,String voterId, BigDecimal offer){
        AuctionForm auctionForm = auctionList.get(auctionId);
        auctionForm.setVoted(auctionForm.getVoted()+1);
        auctionForm.addBuyers(voterId,offer);
        logger.info("increment votes "+auctionForm.getBuyers().get(voterId));
    }
    public BigDecimal closeAuction(String auctionId,String voterId){
        AuctionForm auctionForm = auctionList.get(auctionId);
        auctionForm.setCloseAt(Instant.now());
        auctionForm.setCost(auctionForm.getCost()+10);// just now static
        return auctionForm.getBuyers().get(voterId);
    }
}

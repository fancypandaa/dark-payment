package com.bank.payment.auctions.factory;

import com.bank.payment.auctions.domain.AuctionForm;
import com.bank.payment.auctions.domain.AuctionModel;
import com.bank.payment.concurrency.BalanceFactory;
import com.bank.payment.concurrency.BalanceOptions;
import com.bank.payment.domain.BalanceTypes;
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
    BalanceOptions balanceOptions;

    public AuctionFactory() {
        balanceOptions = new BalanceOptions();
    }

    private AuctionForm createNewAuctionForm(AuctionModel auctionModel){
        BalanceFactory balanceFactory=balanceOptions.createBalance("localCurrency",0.17, BalanceTypes.FOREIGN_CURRENCY);
        AuctionForm auctionForm= new AuctionForm();
        auctionForm.setAuctionId(auctionModel.getAuctionId());
        auctionForm.setState("open");
        auctionForm.setCost(10.0);
        auctionForm.setOpenAt(Instant.now());
        auctionForm.setCloseAt(auctionForm.getOpenAt().plus(30, ChronoUnit.MINUTES));
        BigDecimal openingAmount=balanceFactory.calculateInitialCurrencyEquivalent(
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
        if(auctionForm.getAuctionId().isEmpty()){
            logger.info("Auction with ID: "+ auctionId+" has been already gone...");
            return ;
        }
        auctionForm.setCost(auctionForm.getCost()*2);
        auctionForm.setCloseAt(auctionForm.getCloseAt().plus(30,ChronoUnit.MINUTES));
        auctionForm.setState("extra");
        logger.info("Auction Extended successfully, wish you luck!!!"+ auctionForm.toString());
    }
    public void addVote(String auctionId,String voterId, BigDecimal offer){
        AuctionForm auctionForm = auctionList.get(auctionId);
        if(auctionForm.getBuyers().get(voterId) != null){
            auctionForm.getBuyers().put(voterId,offer);
            logger.info("exist voter submit new offer "+auctionForm.toString());
        }
        else {
            auctionForm.addBuyers(voterId,offer);
            auctionForm.setVoted(auctionForm.getVoted() + 1);
            auctionForm.setCost(auctionForm.getCost() + 0.25); // 25 cent for every vote
            auctionForm.addBuyers(voterId, offer);
            logger.info("increment votes "+auctionForm.toString());
        }
    }

    /// worker solution .........??????? new branch full
    public BigDecimal closeAuction(String auctionId,String voterId){
        AuctionForm auctionForm = auctionList.get(auctionId);
        auctionForm.setCloseAt(Instant.now());
        auctionForm.setCost(auctionForm.getCost()+10);// just now static
        return auctionForm.getBuyers().get(voterId);
    }
}

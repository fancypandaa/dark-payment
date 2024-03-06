package com.bank.payment.auctions.factory;

import com.bank.payment.auctions.domain.AuctionForm;
import com.bank.payment.auctions.domain.AuctionModel;
import com.bank.payment.auctions.service.AuctionService;
import com.bank.payment.concurrency.BalanceFactory;
import com.bank.payment.concurrency.BalanceOptions;
import com.bank.payment.domain.BalanceTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.Instant;

@Component
public class AuctionFactory {


    private static final Logger logger= LoggerFactory.getLogger(AuctionFactory.class);
    BalanceOptions balanceOptions;
    @Autowired
    AuctionService auctionService;

    public AuctionFactory() {
        balanceOptions = new BalanceOptions();
    }


    private AuctionForm createNewAuctionForm(AuctionModel auctionModel){

        BalanceFactory balanceFactory=balanceOptions.createBalance(BalanceTypes.LOCAL_CURRENCY.type,0.17, BalanceTypes.FOREIGN_CURRENCY);
        AuctionForm auctionForm= new AuctionForm();
        auctionForm.setAuctionId(auctionModel.getAuctionId());
        auctionForm.setState("open");
        auctionForm.setFrom(auctionModel.getCurrencyType());
        auctionForm.setTo(auctionModel.getCurrencyTo());
        auctionForm.setCost(10.0);
        auctionForm.setOpenAt(Instant.now());
        auctionForm.setCloseAt(auctionForm.getOpenAt().plus(30, ChronoUnit.MINUTES));
        BigDecimal openingAmount=balanceFactory.calculateInitialCurrencyEquivalent(
                BigDecimal.valueOf(auctionModel.getCurrentAmount()),0.5,BalanceTypes.FOREIGN_CURRENCY);
        logger.info("OpeninngPrice "+auctionModel.getCurrentAmount());
        auctionForm.setOpeningPrice(openingAmount);
        return auctionForm;
    }

    public AuctionForm findFormById(String auctionId){
        Optional<AuctionForm> optionalAuctionForm = Optional.of(auctionService.getAuctionForm(auctionId));
        if(optionalAuctionForm.isEmpty()){
            return null;
        }
        return optionalAuctionForm.get();
    }
    public AuctionForm getOrCreateAuction(AuctionModel auctionModel){
        Optional<AuctionForm> result = Optional.ofNullable(auctionService.getAuctionForm(auctionModel.getAuctionId()));
        if(result.isEmpty()){
            AuctionForm auctionForm=createNewAuctionForm(auctionModel);
            auctionService.putAuctionForm(auctionForm.getAuctionId(), auctionForm);
            result= Optional.of(auctionForm);
        }
//        logger.info("MAP SIZE::");
        logger.info("Your auction form info "+result.toString());
        return result.get();
    }

    public void removeAuction(String auctionId){
        Optional<AuctionForm> optionalAuctionForm = Optional.ofNullable(auctionService.getAuctionForm(auctionId));
        if(optionalAuctionForm.isPresent()) {
            auctionService.deleteAuctionForm(auctionId);
        }
    }
    public void extendAuction(String auctionId){
        Optional<AuctionForm> optionalAuctionForm = Optional.ofNullable(auctionService.getAuctionForm(auctionId));
        if(!optionalAuctionForm.isPresent()){
            logger.info("Auction with ID: "+ auctionId+" has been already gone...");
            return ;
        }
        AuctionForm auctionForm = optionalAuctionForm.get();
        auctionForm.setCost(auctionForm.getCost()*2);
        auctionForm.setCloseAt(auctionForm.getCloseAt().plus(30,ChronoUnit.MINUTES));
        auctionForm.setState("extra");
        logger.info("Auction Extended successfully, wish you luck!!!"+ auctionForm.toString());
    }
    public void addVote(String auctionId,String voterId, BigDecimal offer)  {
        Optional<AuctionForm> optionalAuctionForm = Optional.ofNullable(auctionService.getAuctionForm(auctionId));
        if(!optionalAuctionForm.isPresent()){
            logger.info("Auction with ID: "+ auctionId+" not found");
            return ;
        }
        logger.info(optionalAuctionForm.get().toString());
        AuctionForm auctionForm=optionalAuctionForm.get();
        if(auctionForm.getBuyers().get(voterId) !=null){
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
        auctionService.putAuctionForm(auctionId,auctionForm);
    }

    /// worker solution .........??????? new branch full
    public BigDecimal closeAuction(String auctionId,String voterId){
        Optional<AuctionForm> optionalAuctionForm = Optional.ofNullable(auctionService.getAuctionForm(auctionId));
        if(!optionalAuctionForm.isPresent()){
            logger.info("Auction with ID: "+ auctionId+" has been already gone...");
            return null;
        }
        AuctionForm auctionForm= optionalAuctionForm.get();
        auctionForm.setCloseAt(Instant.now());
        auctionForm.setCost(auctionForm.getCost()+10);// just now static
        return auctionForm.getBuyers().get(voterId);
    }
}

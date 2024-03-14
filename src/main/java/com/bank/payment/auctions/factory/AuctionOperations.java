package com.bank.payment.auctions.factory;

import com.bank.payment.auctions.domain.AuctionForm;
import com.bank.payment.auctions.domain.AuctionModel;
import com.bank.payment.auctions.domain.ProcessState;
import com.bank.payment.concurrency.BalanceFactory;
import com.bank.payment.concurrency.BalanceOptions;
import com.bank.payment.domain.AuctionLogs;
import com.bank.payment.domain.BalanceTypes;
import com.bank.payment.dto.model.AuctionLogsDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
public class AuctionOperations {
    private static final Logger logger= LoggerFactory.getLogger(AuctionOperations.class);

    protected static AuctionForm createNewAuctionForm(BalanceOptions balanceOptions,AuctionModel auctionModel){
        AuctionForm auctionForm= new AuctionForm();
        auctionForm.setAuctionId(auctionModel.getAuctionId());
        auctionForm.setState("open");
        auctionForm.setFrom(auctionModel.getCurrencyType());
        auctionForm.setTo(auctionModel.getCurrencyTo());
        auctionForm.setCost(10.0);
        auctionForm.setProcessState(auctionModel.getProcessState());
        auctionForm.setOpenAt(Instant.now());
        auctionForm.setCloseAt(auctionForm.getOpenAt().plus(30, ChronoUnit.MINUTES));
        auctionForm.setOpeningPrice(getOpeningAmount(balanceOptions,auctionModel));
        logger.info("OpeninngPrice "+auctionModel.getCurrentAmount());
        return auctionForm;
    }
    private static BigDecimal getOpeningAmount(BalanceOptions balanceOptions,AuctionModel auctionModel){
        BalanceFactory balanceFactory=balanceOptions.createBalance(auctionModel.getCurrencyType(),0.17, BalanceTypes.valueOf(auctionModel.getCurrencyTo()));
        if(auctionModel.getProcessState() == ProcessState.RED){
            BigDecimal  openingAmount=balanceFactory.calculateInitialCurrencyEquivalent(
                    BigDecimal.valueOf(auctionModel.getCurrentAmount()),0.5,BalanceTypes.valueOf(auctionModel.getCurrencyTo()));
            return openingAmount;
        }
        return BigDecimal.valueOf(auctionModel.getCurrentAmount());
    }
    protected static BigDecimal getInsurancePrice(BigDecimal amount){
        return amount.multiply(BigDecimal.valueOf(0.01));
    }
    protected static boolean checkUserInsurance(ArrayList<String> voters,String voterId){
        if(voters.contains(voterId)){
            return true;
        }
        return false;
    }
    protected static AuctionLogsDTO createAuctionsLog(AuctionForm auctionForm,String voterId){
        AuctionLogsDTO auctionLogsDTO = new AuctionLogsDTO();
        auctionLogsDTO.setAuctionId(auctionForm.getAuctionId());
        auctionLogsDTO.setCost(auctionForm.getCost());
        auctionLogsDTO.setFrom(auctionForm.getFrom());
        auctionLogsDTO.setTo(auctionForm.getTo());
        auctionLogsDTO.setVoted(auctionForm.getVoted());
        auctionLogsDTO.setCloseAt(auctionForm.getCloseAt());
        auctionLogsDTO.setOpeningPrice(auctionForm.getOpeningPrice());
        auctionLogsDTO.setState(auctionForm.getState());
        auctionLogsDTO.setProcessState(auctionForm.getProcessState());
        auctionLogsDTO.setBuyer(voterId);
        return auctionLogsDTO;
    }

}

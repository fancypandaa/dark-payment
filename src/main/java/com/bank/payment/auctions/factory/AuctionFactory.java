package com.bank.payment.auctions.factory;

import com.bank.payment.auctions.domain.AuctionForm;
import com.bank.payment.auctions.domain.AuctionModel;
import com.bank.payment.auctions.domain.ProcessState;
import com.bank.payment.auctions.service.AuctionService;
import com.bank.payment.concurrency.BalanceOptions;
import com.bank.payment.dto.model.AuctionLogsDTO;
import com.bank.payment.service.AuctionLogsService;
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
    private final BalanceOptions balanceOptions;
    private final AuctionLogsService auctionLogsService;
    private final AuctionService auctionService;

    public AuctionFactory(BalanceOptions balanceOptions, AuctionLogsService auctionLogsService, AuctionService auctionService) {
        this.balanceOptions = balanceOptions;
        this.auctionLogsService = auctionLogsService;
        this.auctionService = auctionService;
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
            AuctionForm auctionForm=AuctionOperations.createNewAuctionForm(balanceOptions,auctionModel);
            auctionService.putAuctionForm(auctionForm.getAuctionId(), auctionForm);
            auctionService.setExpireTime(auctionForm.getAuctionId(),auctionForm.getCloseAt());
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
        auctionService.putAuctionForm(auctionId,auctionForm);
        auctionService.setExpireTime(auctionId,auctionForm.getCloseAt());
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
        if(!AuctionOperations.checkUserInsurance(auctionForm.getVotersList(),voterId)){
            return;
        }
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
    public BigDecimal acceptOffer(String auctionId,String voterId){
        Optional<AuctionForm> optionalAuctionForm = Optional.ofNullable(auctionService.getAuctionForm(auctionId));
        if(!optionalAuctionForm.isPresent()){
            logger.info("Auction with ID: "+ auctionId+" has been already gone...");
            return null;
        }
        AuctionForm auctionForm= optionalAuctionForm.get();
        if(auctionForm.getProcessState() == ProcessState.RED){
//            3rd party transaction through paypal
        }
        else{
//            cash without tax

        }
        auctionForm.setCloseAt(Instant.now());
        AuctionLogsDTO auctionLogsDTO = AuctionOperations.createAuctionsLog(auctionForm,voterId);
        auctionLogsService.createNewAuctionLog(auctionLogsDTO);
        return auctionForm.getBuyers().get(voterId);
    }
    public void payAuctionInsurance(String auctionId,String voterId){
        Optional<AuctionForm> optionalAuctionForm = Optional.ofNullable(auctionService.getAuctionForm(auctionId));
//        3rd party payment method
        if(!optionalAuctionForm.isPresent()){
            logger.info("Auction with ID: "+ auctionId+" has been already gone...");
            return;
        }
        AuctionForm auctionForm= optionalAuctionForm.get();
        BigDecimal price = AuctionOperations.getInsurancePrice(auctionForm.getOpeningPrice());
        logger.info("voter submit insurance price successfully"+price);
        auctionForm.getVotersList().add(voterId);
        auctionService.putAuctionForm(auctionId,auctionForm);
    }
}

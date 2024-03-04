package com.bank.payment.auctions.controller;

import com.bank.payment.auctions.service.AuctionService;
import com.bank.payment.auctions.factory.AuctionBuilder;
import com.bank.payment.auctions.domain.AuctionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping(AuctionController.BASE_URL)
public class AuctionController {
    private static final Logger log= LoggerFactory.getLogger(AuctionController.class);
    public static final String BASE_URL = "/api/auction";
    @Autowired
    AuctionBuilder auctionBuilder;
    @Autowired
    private AuctionService auctionService;

    @PostMapping("/publish")
    public ResponseEntity<String> publish(@RequestBody AuctionModel auctionModel){
        try {
            auctionService.publish(auctionModel);
            log.info("new auction model published......",auctionModel.getAuctionId());
            return new ResponseEntity<>("success", HttpStatus.OK);
        }
        catch (Exception ex){
            log.error(ex.getMessage());
            return new ResponseEntity<>("failed",HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/makeOffer")
    public ResponseEntity<String> submitOffer(
            @RequestParam("auctionId") String auctionId,
            @RequestParam("offer") BigDecimal offer,
            @RequestParam("userId") String userId
    ){
        try {
           log.info("new offer was submitted for "+auctionId);
            auctionBuilder.addVote(auctionId,userId,offer);
            return new ResponseEntity<>("Done",HttpStatus.OK);
        }
        catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/extendAuction/{auctionId}")
    public ResponseEntity<String> extendAuction(@PathVariable String auctionId){
        try {
            auctionBuilder.extendAuction(auctionId);
            return new ResponseEntity<>("Done",HttpStatus.OK);
        }
        catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/acceptOffer")
    public ResponseEntity<String> makeDeal(
            @RequestParam String auctionId,
            @RequestParam String voterId){
        try {
            auctionBuilder.closeAuction(auctionId,Optional.of(voterId));
            return new ResponseEntity<>("Done",HttpStatus.OK);
        }
        catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/closeAuction")
    public ResponseEntity<String> closeAuction(
            @RequestParam String auctionId){

            auctionBuilder.closeAuction(auctionId,Optional.empty());
            return new ResponseEntity<>("Done",HttpStatus.OK);

    }
}

package com.bank.payment.auctions.publisher;

import com.bank.payment.auctions.subscriber.AuctionBuilder;
import com.bank.payment.auctions.subscriber.AuctionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;
@RestController
@RequestMapping(AuctionController.BASE_URL)
public class AuctionController {
    public static final String BASE_URL = "/api/auction";

    @Autowired
    private AuctionService auctionService;
    @Autowired
    AuctionBuilder auctionBuilder;

    @PostMapping("/publish")
    public ResponseEntity<String> publish(@RequestBody AuctionModel auctionModel){
        auctionModel.setCreatedAt(new Date().toLocaleString());
        auctionService.publish(auctionModel);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
    @PostMapping("/makeOffer")
    public ResponseEntity<String> submitOffer(
            @RequestParam String auctionId,
            @RequestParam String voterId,
            @RequestParam BigDecimal offer
    ){
        try {
            auctionBuilder.addVote(auctionId,voterId,offer);
            return new ResponseEntity<>("Done",HttpStatus.OK);
        }
        catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/extendAuction")
    public ResponseEntity<String> extendAuction(
            @RequestParam String auctionId
    ){
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
        try {
            auctionBuilder.closeAuction(auctionId,Optional.empty());
            return new ResponseEntity<>("Done",HttpStatus.OK);
        }
        catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}

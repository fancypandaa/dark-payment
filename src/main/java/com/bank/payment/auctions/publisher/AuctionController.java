package com.bank.payment.auctions.publisher;

import com.bank.payment.auctions.subscriber.AuctionBuilder;
import com.bank.payment.auctions.subscriber.AuctionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@RestController
@RequestMapping(AuctionController.BASE_URL)
public class AuctionController {
    private static final Logger log= LoggerFactory.getLogger(AuctionController.class);
    public static final String BASE_URL = "/api/auction";
    LinkedList<AuctionModel> ll= new LinkedList<>();
    @Autowired
    AuctionBuilder auctionBuilder;
    @Autowired
    private AuctionService auctionService;

    @PostMapping("/publish")
    public ResponseEntity<String> publish(@RequestBody AuctionModel auctionModel){
        UUID uuid= UUID.randomUUID();
        auctionModel.setAuctionId(uuid.toString());
        auctionModel.setCreatedAt(new Date().toLocaleString());
        auctionService.publish(auctionModel);
        log.info("new auction model published......",auctionModel.getAuctionId());
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
    @PostMapping("/makeOffer")
    public ResponseEntity<String> submitOffer(
            @RequestParam("auctionId") String auctionId,
            @RequestParam("offer") BigDecimal offer
    ){
        try {
            UUID uuid=UUID.randomUUID();
            String voterId = uuid.toString();
            log.info("new offer was submitted by" ,voterId,auctionId);
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

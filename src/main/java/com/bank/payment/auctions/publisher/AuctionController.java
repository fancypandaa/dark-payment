package com.bank.payment.auctions.publisher;

import com.bank.payment.auctions.subscriber.AuctionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;
@RestController
@RequestMapping(AuctionController.BASE_URL)
public class AuctionController {
    public static final String BASE_URL = "/api/auction";

    @Autowired
    private AuctionService auctionService;

    @PostMapping("/publish")
    public String publish(@RequestBody AuctionModel auctionModel){
        auctionModel.setCreatedAt(new Date().toLocaleString());
        auctionService.publish(auctionModel);
        return "Success";
    }
}

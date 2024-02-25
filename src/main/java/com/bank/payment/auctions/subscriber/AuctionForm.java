package com.bank.payment.auctions.subscriber;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuctionForm {
    private String auctionId;
    private String state;
    private int voted;
    private double cost;
    private Date openAt;
    private Date closeAt;
    private HashMap<String, BigDecimal> buyers= new HashMap<>();
    public AuctionForm addBuyers(String voterId, BigDecimal offer){
        this.buyers.put(voterId,offer);
        return this;
    }
}

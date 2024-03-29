package com.bank.payment.auctions.domain;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuctionForm implements Serializable {
    private String auctionId;
    private String state;
    private BigDecimal openingPrice;
    private int voted;
    private double cost;
    private Instant openAt;
    private Instant closeAt;
    private String from;
    private String to;
    private ProcessState processState;
    private ArrayList<String> votersList= new ArrayList<>();
    private HashMap<String, BigDecimal> buyers= new HashMap<>();
    public AuctionForm addBuyers(String voterId, BigDecimal offer){
        this.buyers.put(voterId,offer);
        return this;
    }
}

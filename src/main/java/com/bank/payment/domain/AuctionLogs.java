package com.bank.payment.domain;

import com.bank.payment.auctions.domain.AuctionForm;
import com.bank.payment.auctions.domain.ProcessState;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;


@Entity
@Getter
@Setter
public class AuctionLogs {
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
    private String buyer;
}

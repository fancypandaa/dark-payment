package com.bank.payment.dto.model;

import com.bank.payment.auctions.domain.ProcessState;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
@Data
public class AuctionLogsDTO {
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

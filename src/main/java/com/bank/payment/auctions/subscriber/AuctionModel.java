package com.bank.payment.auctions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuctionModel implements Serializable {
    private String auctionId;
    private String userId;
    private Long currentAmount;
    private String currencyType;
    private String currencyTo;
    private Date createdAt;
}

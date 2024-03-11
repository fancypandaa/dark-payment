package com.bank.payment.auctions.service;

import com.bank.payment.auctions.domain.AuctionForm;
import com.bank.payment.auctions.domain.AuctionModel;
import java.time.Instant;

public interface AuctionService {
    Long publish(AuctionModel auctionModel);
    void putAuctionForm(String auctionId,AuctionForm auctionForm);
    void setExpireTime(String key,Instant date);
    AuctionForm getAuctionForm(String auctionId);
    void deleteAuctionForm(String auctionId);
}

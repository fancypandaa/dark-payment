package com.bank.payment.auctions.service;

import com.bank.payment.auctions.domain.AuctionForm;
import com.bank.payment.auctions.domain.AuctionModel;

public interface AuctionService {
    Long publish(AuctionModel auctionModel);
    void putAuctionForm(String auctionId,AuctionForm auctionForm);
    AuctionForm getAuctionForm(String auctionId);
    void deleteAuctionForm(String auctionId);
}

package com.bank.payment.auctions.service;

import com.bank.payment.auctions.domain.AuctionModel;

public interface AuctionService {
    Long publish(AuctionModel auctionModel);
}

package com.bank.payment.auctions.publisher;

import com.bank.payment.auctions.subscriber.AuctionModel;

public interface AuctionService {
    Long publish(AuctionModel auctionModel);
}

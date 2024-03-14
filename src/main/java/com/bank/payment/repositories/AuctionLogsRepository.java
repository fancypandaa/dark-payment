package com.bank.payment.repositories;

import com.bank.payment.domain.AuctionLogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionLogsRepository extends JpaRepository<AuctionLogs,Long> {
}

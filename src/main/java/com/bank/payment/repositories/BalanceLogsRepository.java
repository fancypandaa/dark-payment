package com.bank.payment.repositories;

import com.bank.payment.domain.BalanceLogs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;
@Repository
public interface BalanceLogsRepository extends JpaRepository<BalanceLogs, UUID> {
    @Override
    Page<BalanceLogs> findAll(Pageable pageable);
    List<BalanceLogs> findBalanceLogsByBalanceId(Long balanceId);
}

package com.bank.payment.repositories;

import com.bank.payment.domain.Balance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;
@Repository
public interface BalanceRepository extends JpaRepository<Balance,Long> {
    List<Balance> findBalanceByAccountId(Long accountId);
}

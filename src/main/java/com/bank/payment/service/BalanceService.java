package com.bank.payment.service;


import com.bank.payment.api.model.BalanceDTO;
import java.util.*;
public interface BalanceService {
    BalanceDTO findBalanceById(Long id);
    List<BalanceDTO> findBalanceByAccountId(Long accountId);
    BalanceDTO createNewBalance(BalanceDTO balanceDTO);
    BalanceDTO saveBalanceByDTO(Long id,BalanceDTO balanceDTO);
    BalanceDTO patchBalance(Long id,BalanceDTO balanceDTO);
    void removeBalanceById(Long id);

}

package com.bank.payment.service;

import com.bank.payment.api.model.BalanceLogsDTO;
import com.bank.payment.api.model.BalanceLogsListDTO;
import org.springframework.data.domain.Page;
import java.util.*;
public interface BalanceLogsService {
    Page<BalanceLogsDTO> getAllBalanceLogs(int page,int size);
    BalanceLogsDTO getBalanceLogById( UUID id);
    BalanceLogsListDTO findBalanceLogsByBalanceId(Long balanceId);
    BalanceLogsDTO createBalanceLogs(BalanceLogsDTO balanceLogsDTO);
    BalanceLogsDTO saveBalanceLogsByDTO(UUID id,BalanceLogsDTO balanceLogsDTO);
    void removeBalanceLogsById(UUID id);

}

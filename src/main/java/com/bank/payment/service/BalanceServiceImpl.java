package com.bank.payment.service;

import com.bank.payment.api.mapper.BalanceMapper;
import com.bank.payment.api.model.BalanceDTO;
import com.bank.payment.domain.Balance;
import com.bank.payment.repositories.BalanceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
@Service
public class BalanceServiceImpl implements BalanceService{
    private static final Logger log = LoggerFactory.getLogger(BalanceServiceImpl.class);

    private final BalanceRepository balanceRepository;
    private final BalanceMapper balanceMapper;

    public BalanceServiceImpl(BalanceRepository balanceRepository, BalanceMapper balanceMapper) {
        this.balanceRepository = balanceRepository;
        this.balanceMapper = balanceMapper;
    }

    @Override
    public BalanceDTO findBalanceById(Long id) {
        return balanceRepository.findById(id)
                .map(balanceMapper::balanceToBalanceDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public List<BalanceDTO> findBalanceByAccountId(Long accountId) {
        return balanceRepository.findBalanceByAccountId(accountId)
                .stream().map(balanceMapper::balanceToBalanceDTO).collect(Collectors.toList());
    }
    private BalanceDTO savedBalanceByDTO(Balance balance){
        Balance newBalance = balanceRepository.save(balance);
        return balanceMapper.balanceToBalanceDTO(newBalance);
    }
    @Override
    public BalanceDTO createNewBalance(BalanceDTO balanceDTO) {
        return savedBalanceByDTO(balanceMapper.balanceDtoToBalance(balanceDTO));
    }

    @Override
    public BalanceDTO saveBalanceByDTO(Long id, BalanceDTO balanceDTO) {
        Balance balance = balanceMapper.balanceDtoToBalance(balanceDTO);
        balance.setId(id);
        return savedBalanceByDTO(balance);
    }

    @Override
    public BalanceDTO patchBalance(Long id, BalanceDTO balanceDTO) {
        Optional<Balance> optionalBalance = balanceRepository.findById(id);
        if(!optionalBalance.isPresent()) return null;
        Balance balance= optionalBalance.get();
        if(balance.getTotalCoins() != balanceDTO.getTotalCoins()){
            balance.setTotalCoins(balanceDTO.getTotalCoins());
        }
        if(balance.getTotalPoints() != balanceDTO.getTotalPoints()){
            balance.setTotalPoints(balanceDTO.getTotalPoints());
        }
        if(balance.getLocalBalance() != balanceDTO.getLocalBalance()){
            balance.setLocalBalance(balanceDTO.getLocalBalance());
        }
        if(balance.getForeignBalance() != balanceDTO.getForeignBalance()){
            balance.setForeignBalance(balanceDTO.getForeignBalance());
        }
        return saveBalanceByDTO(id,balanceDTO);

    }

    @Override
    public void removeBalanceById(Long id) {
        balanceRepository.deleteById(id);
    }
}

package com.bank.payment.api.mapper;

import com.bank.payment.api.model.BalanceDTO;
import com.bank.payment.domain.*;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BalanceMapperTest {
    private static final Long ID= 3L;
    private static final BalanceTypes BALANCE_TYPES =BalanceTypes.BLOCK_CHAIN;
    private static final double CURRENT_BALANCE=5000;
    private static final Long TOTAL_COINS=500L;
    private static final double TOTAL_POINTS=10;
    private static final String BALANCE_STATE="GOOD";
    BalanceMapper balanceMapper = BalanceMapper.INSTANCE;

    @Test
    void balanceToBalanceDTO() {
        Balance balance= new Balance();
        balance.setId(ID);
        balance.setBalanceTypes(BALANCE_TYPES);
        balance.setLocalBalance(CURRENT_BALANCE);
        balance.setTotalCoins(TOTAL_COINS);
        balance.setTotalPoints(TOTAL_POINTS);
        balance.setBalance_State(BALANCE_STATE);
        BalanceDTO balanceDTO = balanceMapper.balanceToBalanceDTO(balance);
        assertNotNull(balanceDTO);
        assertEquals(ID,balanceDTO.getId());
        assertEquals(BALANCE_STATE,balanceDTO.getBalance_State());
    }

    @Test
    void balanceDtoToBalance() {
        BalanceDTO balanceDto= new BalanceDTO();
        balanceDto.setId(ID);
        balanceDto.setBalanceTypes(BALANCE_TYPES);
        balanceDto.setLocalBalance(CURRENT_BALANCE);
        balanceDto.setTotalCoins(TOTAL_COINS);
        balanceDto.setTotalPoints(TOTAL_POINTS);
        Balance balance = balanceMapper.balanceDtoToBalance(balanceDto);
        assertNotNull(balance);
        assertEquals(ID,balance.getId());
        assertEquals(TOTAL_COINS,balance.getTotalCoins());
    }
}
package com.bank.payment.dto.model;

import com.bank.payment.domain.Account;
import com.bank.payment.domain.BalanceLogs;
import com.bank.payment.domain.BalanceTypes;

import lombok.Data;

import java.util.ArrayList;
@Data
public class BalanceDTO {
    private Long id;
    private BalanceTypes balanceTypes;
    private double localBalance;
    private double foreignBalance;
    private Long totalCoins;
    private String Balance_State;
    private ArrayList<BalanceLogs> balanceLog;
}

package com.bank.payment.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.*;
@Entity
@Getter
@Setter
public class Balance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated
    private BalanceTypes balanceTypes;
    private double localBalance;
    private double foreignBalance;
    private Long totalCoins;
    private double totalPoints;
    private String Balance_State;
    @OneToOne
    Plan plan;
    @ManyToOne
    Account account;
    @OneToMany(mappedBy = "balance")
    private ArrayList<BalanceLogs> balanceLog= new ArrayList<>();
    public Balance addBalanceLogs(BalanceLogs log){
        log.setBalance(this);
        this.balanceLog.add(log);
        return this;
    }
}


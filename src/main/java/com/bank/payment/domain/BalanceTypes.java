package com.bank.payment.domain;

public enum BalanceTypes {
    BLOCK_CHAIN("coins"),
    LOCAL_CURRENCY("localCurrency"),
    FOREIGN_CURRENCY("foreignCurrency");
    public final String type;

    BalanceTypes(String type) {
        this.type = type;

    }

    }

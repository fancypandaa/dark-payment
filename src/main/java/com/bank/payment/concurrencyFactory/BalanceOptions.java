package com.bank.payment.concurrencyFactory;

import com.bank.payment.domain.BalanceTypes;

public class BalanceOptions {

    public BalanceFactory createBalance(String type,Double taxPercentage, BalanceTypes currencyEquivalent){
        BalanceFactory balanceFactory = null;
        if(type.equals("localCurrency")){
            balanceFactory = new LocalCurrency(taxPercentage,currencyEquivalent);
        }
        else if(type.equals("foreignCurrency")){
            balanceFactory = new ForeignCurrency(taxPercentage,currencyEquivalent);
        }
        else if(type.equals("points")){
            balanceFactory = new Points(taxPercentage,currencyEquivalent);
        }
        else if(type.equals("coins")){
            balanceFactory = new Coins(taxPercentage,currencyEquivalent);
        }
        return balanceFactory;
    }

}

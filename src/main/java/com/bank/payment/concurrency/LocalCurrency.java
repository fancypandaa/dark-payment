package com.bank.payment.concurrency;

import com.bank.payment.domain.BalanceTypes;

import java.math.BigDecimal;

public class LocalCurrency extends BalanceFactory {

    public LocalCurrency(Double taxPercentage,BalanceTypes currencyEquivalent) {
        this.taxPercentage = taxPercentage;
        this.currencyEquivalent = currencyEquivalent;
    }

    @Override
    public BigDecimal getAvailableToUseAfterTax(BigDecimal amount) {
//        25% * (100% tax) + 50% * (50% tax) + 100% * (25% tax)
        Double afterTax = (amount.doubleValue() * 0.25 * (taxPercentage*0.15)) +
                (amount.doubleValue() * 0.5 * (taxPercentage*0.5))+
                (amount.doubleValue() *0.25 * (taxPercentage*0.35));
        return amount.subtract(BigDecimal.valueOf(afterTax));
    }
    @Override
    public BigDecimal calculateInitialCurrencyEquivalent(BigDecimal amount, Double equivalentPrice, BalanceTypes currency) {
        return null;
    }

}

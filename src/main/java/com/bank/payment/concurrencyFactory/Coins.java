package com.bank.payment.concurrencyFactory;



import lombok.Setter;

import java.math.BigDecimal;

@Setter
public class Coins extends BalanceFactory {
    private static double onlineTax = 0.2;
    public Coins(Double taxPercentage,String currencyEquivalent) {
        this.taxPercentage = taxPercentage;
        this.currencyEquivalent = currencyEquivalent;
    }

    @Override
    public BigDecimal getAvailableToUseAfterTax(BigDecimal credit) {
        BigDecimal afterTax = BigDecimal.valueOf((credit.intValue() * taxPercentage * onlineTax));
        return credit.subtract(afterTax);
    }
    @Override
    public BigDecimal calculateCurrencyEquivalent(BigDecimal amount,String currency) {
        double equivalentAmount = amount.doubleValue() * CurrencyValue.valueOf(currency).getValue();
        return BigDecimal.valueOf(equivalentAmount);
    }
}

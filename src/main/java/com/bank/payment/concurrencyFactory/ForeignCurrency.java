package com.bank.payment.concurrencyFactory;

import java.math.BigDecimal;

public class ForeignCurrency extends BalanceFactory {

    public ForeignCurrency(Double taxPercentage,String currencyEquivalent) {
        this.taxPercentage = taxPercentage;
        this.currencyEquivalent = currencyEquivalent;
    }

    @Override
    public BigDecimal getAvailableToUseAfterTax(BigDecimal credit) {
        BigDecimal afterTax = BigDecimal.valueOf((credit.doubleValue() * taxPercentage));
        return credit.subtract(afterTax);
    }
    @Override
    public BigDecimal calculateCurrencyEquivalent(BigDecimal amount,String currency) {
        return null;
    }
}

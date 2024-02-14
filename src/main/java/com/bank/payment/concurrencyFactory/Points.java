package com.bank.payment.concurrencyFactory;

import java.math.BigDecimal;

public class Points extends BalanceFactory {

    public Points(Double taxPercentage,String currencyEquivalent) {
        this.taxPercentage = taxPercentage;
        this.currencyEquivalent = currencyEquivalent;
    }

    @Override
    public BigDecimal getAvailableToUseAfterTax(BigDecimal amount) {
        BigDecimal afterTax = BigDecimal.valueOf((amount.doubleValue() * taxPercentage));
        return amount.subtract(afterTax);
    }
    @Override
    public BigDecimal calculateCurrencyEquivalent(BigDecimal amount,String currency) {
        return null;
     }
}

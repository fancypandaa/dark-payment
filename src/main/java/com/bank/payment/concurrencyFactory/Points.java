package com.bank.payment.concurrencyFactory;

import com.bank.payment.domain.BalanceTypes;

import java.math.BigDecimal;

public class Points extends BalanceFactory {

    public Points(Double taxPercentage,BalanceTypes currencyEquivalent) {
        this.taxPercentage = taxPercentage;
        this.currencyEquivalent = currencyEquivalent;
    }

    @Override
    public BigDecimal getAvailableToUseAfterTax(BigDecimal amount) {
        Double afterTax = ((amount.doubleValue() *.15) * (0.25 * taxPercentage)) +
                (amount.doubleValue() * 0.5 * (taxPercentage*0.5))+
                (amount.doubleValue()  *0.35* (taxPercentage*0.25));
        return amount.subtract(BigDecimal.valueOf(Math.ceil(afterTax)));
    }
    @Override
    public BigDecimal calculateCurrencyEquivalent(BigDecimal amount, BalanceTypes currency) {
        return null;
     }
}

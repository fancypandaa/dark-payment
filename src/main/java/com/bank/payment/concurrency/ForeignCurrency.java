package com.bank.payment.concurrency;

import com.bank.payment.domain.BalanceTypes;

import java.math.BigDecimal;

public class ForeignCurrency extends BalanceFactory {

    public ForeignCurrency(Double taxPercentage,BalanceTypes currencyEquivalent) {
        this.taxPercentage = taxPercentage;
        this.currencyEquivalent = currencyEquivalent;
    }

    @Override
    public BigDecimal getAvailableToUseAfterTax(BigDecimal amount) {
//        25% * (75% tax) + 50% * (50% tax) + 100% * (25% tax)
        Double afterTax = ((amount.doubleValue() *.75) * (0.25 * taxPercentage)) +
                (amount.doubleValue() * 0.05 * (taxPercentage*0.45))+
                (amount.doubleValue() *0.2 * (taxPercentage*0.30));
        return amount.subtract(BigDecimal.valueOf(afterTax));
    }
    @Override
    public BigDecimal calculateInitialCurrencyEquivalent(BigDecimal amount, Double equivalentPrice, BalanceTypes currency) {
        BigDecimal equivalentAmount = BigDecimal.valueOf(amount.doubleValue()* equivalentPrice);
        return equivalentAmount;
    }
}

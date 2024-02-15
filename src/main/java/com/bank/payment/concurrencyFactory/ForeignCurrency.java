package com.bank.payment.concurrencyFactory;

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
                (amount.doubleValue() * 0.5 * (taxPercentage*0.5))+
                (amount.doubleValue()  * (taxPercentage*0.25));
        return amount.subtract(BigDecimal.valueOf(afterTax));
    }
    @Override
    public BigDecimal calculateCurrencyEquivalent(BigDecimal amount, BalanceTypes currency) {
        return null;
    }
}

package com.bank.payment.concurrency;



import com.bank.payment.domain.BalanceTypes;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
public class Coins extends BalanceFactory {
    public Coins(Double taxPercentage,BalanceTypes currencyEquivalent) {
        this.taxPercentage = taxPercentage;
        this.currencyEquivalent = currencyEquivalent;
    }

    @Override
    public BigDecimal getAvailableToUseAfterTax(BigDecimal amount) {
        Double afterTax = ((amount.doubleValue() *.25) * (0.25 * taxPercentage)) +
                (amount.doubleValue() * 0.5 * (taxPercentage*0.5))+
                (amount.doubleValue()  *0.25* (taxPercentage*0.25));
        return amount.subtract(BigDecimal.valueOf(Math.ceil(afterTax)));
    }
    @Override
    public BigDecimal calculateInitialCurrencyEquivalent(BigDecimal amount, Double equivalentPrice, BalanceTypes currency) {
        BigDecimal equivalentAmount = BigDecimal.valueOf(amount.doubleValue()* equivalentPrice);
        return equivalentAmount;
    }
}

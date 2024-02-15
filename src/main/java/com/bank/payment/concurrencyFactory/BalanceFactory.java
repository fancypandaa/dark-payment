package com.bank.payment.concurrencyFactory;

import com.bank.payment.domain.BalanceTypes;
import lombok.Getter;

import java.math.BigDecimal;
@Getter
abstract public class BalanceFactory {
    Double taxPercentage;
    BalanceTypes currencyEquivalent;

    public abstract BigDecimal getAvailableToUseAfterTax(BigDecimal credit);

    public abstract BigDecimal calculateCurrencyEquivalent(BigDecimal amount, BalanceTypes currency);

    @Override
    public String toString() {
        StringBuffer display = new StringBuffer();
        display.append(" Details \n");
        display.append(taxPercentage + "\n");
        display.append(currencyEquivalent +"\n");
        return display.toString();
    }

}

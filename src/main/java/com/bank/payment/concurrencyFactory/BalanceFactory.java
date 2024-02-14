package com.bank.payment.concurrencyFactory;

import lombok.Getter;

import java.math.BigDecimal;
@Getter
abstract public class BalanceFactory {
    Double taxPercentage;
    String currencyEquivalent;

//    public BigDecimal getTotalAvailableToUse(){
//        BigDecimal afterTax = BigDecimal.valueOf((amount.doubleValue() * taxPercentage));
//        return amount.subtract(afterTax);
//    }
    public abstract BigDecimal getAvailableToUseAfterTax(BigDecimal credit);

    public abstract BigDecimal calculateCurrencyEquivalent(BigDecimal amount,String currency);

    @Override
    public String toString() {
        StringBuffer display = new StringBuffer();
        display.append(" Details \n");
        display.append(taxPercentage + "\n");
        display.append(currencyEquivalent +"\n");
        return display.toString();
    }

}

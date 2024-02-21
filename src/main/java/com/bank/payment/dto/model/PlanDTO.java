package com.bank.payment.dto.model;

import com.bank.payment.domain.BalanceTypes;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class PlanDTO {
    private Long Id;
    private String name;
    private Long minimum;
    private Long maximum;
    @Enumerated
    private BalanceTypes type;
    private double interest;
    private double fees;

}

package com.bank.payment.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String name;
    private Long minimum;
    private Long maximum;
    @Enumerated
    private BalanceTypes type;
    private double interest;
    private double fees;

}

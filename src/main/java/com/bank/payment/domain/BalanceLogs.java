package com.bank.payment.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
@Entity
@Getter
@Setter
public class BalanceLogs {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID Id;
    @Enumerated
    private Operations operation;
    private Boolean OperationStatus;
    private Date createdAt= new Date();
    private Date updatedAt=new Date();
    @ManyToOne
    Balance balance;
}

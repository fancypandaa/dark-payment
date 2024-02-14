package com.bank.payment.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.*;


@Entity
@Getter
@Setter
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,unique = true)
    private String nickName;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String securityCode;
    private String ref_Code;
    private String email;
    @OneToMany(mappedBy = "account")
    private ArrayList<Balance> balanceList= new ArrayList<>();
    public Account setBalance(Balance balance){
        balance.setAccount(this);
        this.balanceList.add(balance);
        return this;
    }

}

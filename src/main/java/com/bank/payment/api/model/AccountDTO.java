package com.bank.payment.api.model;

import com.bank.payment.domain.Balance;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.ArrayList;

@Data
public class AccountDTO {
    private Long id;
    private String name;
    private String password;
    private String penCode;
    private String address;
    private Integer phoneNo;
    private ArrayList<Balance> balanceList;

}

package com.bank.payment.dto.model;

import com.bank.payment.domain.Balance;
import lombok.Data;

import java.util.ArrayList;

@Data
public class AccountDTO {
    private Long id;
    private String nickName;
    private String password;
    private String securityCode;
    private String ref_Code;
    private String email;
    private ArrayList<Balance> balanceList;
}

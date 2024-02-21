package com.bank.payment.dto.model;

import lombok.Data;

@Data
public class AuthDTO {
    String username;
    String password;
    String token;
}

package com.bank.payment.api.model;

import lombok.Data;

@Data
public class AuthDTO {
    String username;
    String password;
    String token;
}

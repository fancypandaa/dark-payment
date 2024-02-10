package com.bank.payment.service;

import org.springframework.security.core.Authentication;

public interface AuthService {
    String generateToken(Authentication authentication);
}

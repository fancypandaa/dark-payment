package com.bank.payment.controller;

import com.bank.payment.api.model.AuthDTO;
import com.bank.payment.domain.AuthUser;
import com.bank.payment.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final Logger log = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    private AuthService authService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthDTO userLogin) throws IllegalAccessException{
        Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    userLogin.getUsername(),
                    userLogin.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        AuthUser userInfo =(AuthUser) authentication.getPrincipal();
        log.info("Token requested for user :{}", authentication.getAuthorities());
        String token = authService.generateToken(authentication);
        AuthDTO authDTO=new AuthDTO();
        authDTO.setUsername(userLogin.getUsername());
        authDTO.setToken(token);
        return ResponseEntity.ok(authDTO);
    }
}

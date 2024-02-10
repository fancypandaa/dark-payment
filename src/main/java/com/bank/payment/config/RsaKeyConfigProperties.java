package com.bank.payment.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Data
@ConfigurationProperties(prefix = "rsa")
public class RsaKeyConfigProperties {
    public RSAPublicKey publicKey;
    public RSAPrivateKey privateKey;
}

package com.bank.payment.service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class ResourceNotFoundException extends RuntimeException{
    private static final Logger log = LoggerFactory.getLogger(ResourceNotFoundException.class);
    public ResourceNotFoundException() {
        log.warn("Error interfaces ......");
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceNotFoundException(Throwable cause) {
        super(cause);
    }

    public ResourceNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

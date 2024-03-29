package com.bank.payment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractRestControllerTest {
    public static String asJsonString(final Object obj){
        try {
            return new ObjectMapper().writeValueAsString(obj);
        }
        catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }
}

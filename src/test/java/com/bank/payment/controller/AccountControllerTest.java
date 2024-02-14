package com.bank.payment.controller;

import com.bank.payment.api.model.AccountDTO;
import com.bank.payment.api.model.AccountListDTO;
import com.bank.payment.service.AccountService;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AccountControllerTest extends AbstractRestControllerTest{
    @InjectMocks
    AccountController accountController;
    @Mock
    AccountService accountService;

    MockMvc mockMvc;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(accountController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler()).build();
    }

    @Test
    void getAllAccounts() throws Exception{
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setNickName("Demo");
        accountDTO.setEmail("aa/bb/cc");
        accountDTO.setPassword("111222333");

        AccountDTO accountDTO1 = new AccountDTO();
        accountDTO1.setNickName("DemoI");
        accountDTO1.setEmail("dd/ee/ff");
        accountDTO1.setPassword("444555666");
        AccountListDTO accountListDTO= new AccountListDTO(Arrays.asList(accountDTO,accountDTO1));
        when(accountService.getAllAccounts()).thenReturn(accountListDTO);
        mockMvc.perform(get(AccountController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.accounts",hasSize(2)));
    }

    @Test
    void getAccount() throws Exception{
        AccountDTO accountDTO= new AccountDTO();
        accountDTO.setNickName("Demo");
        accountDTO.setEmail("aa/bb/cc");
        accountDTO.setPassword("111222333");
        when(accountService.getAccountsById(anyLong())).thenReturn(accountDTO);
        mockMvc.perform(get(AccountController.BASE_URL+"/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",equalTo("Demo")))
                .andExpect(jsonPath("$.address",equalTo("aa/bb/cc")));

    }

    @Test
    void postAccount() throws Exception{
        AccountDTO accountDTO= new AccountDTO();
        accountDTO.setNickName("Demo");
        accountDTO.setEmail("aa/bb/cc");
        accountDTO.setPassword("111222333");

        AccountDTO returnedAccountDTO= new AccountDTO();
        returnedAccountDTO.setNickName(accountDTO.getNickName());
        returnedAccountDTO.setEmail(accountDTO.getEmail());
        returnedAccountDTO.setPassword(accountDTO.getPassword());
        when(accountService.createNewAccount(accountDTO)).thenReturn(returnedAccountDTO);
        mockMvc.perform(post(AccountController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(accountDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name",equalTo("Demo")))
                .andExpect(jsonPath("$.address",equalTo("aa/bb/cc")));
    }

    @Test
    void patchAccount() throws Exception{
        AccountDTO accountDTO= new AccountDTO();
        accountDTO.setNickName("Demo");

        AccountDTO returnedAccountDTO= new AccountDTO();
        returnedAccountDTO.setNickName(accountDTO.getNickName());
        returnedAccountDTO.setEmail("aa/bb/cc");
        when(accountService.patchAccount(anyLong(),any(AccountDTO.class))).thenReturn(returnedAccountDTO);
        mockMvc.perform(patch(AccountController.BASE_URL+"/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(accountDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",equalTo("Demo")))
                .andExpect(jsonPath("$.address",equalTo("aa/bb/cc")));
    }

    @Test
    void updateAccount() throws Exception{
        AccountDTO accountDTO= new AccountDTO();
        accountDTO.setNickName("Demo");
        accountDTO.setEmail("aa/bb/cc");
        accountDTO.setPassword("111222333");

        AccountDTO returnedAccountDTO= new AccountDTO();
        returnedAccountDTO.setNickName(accountDTO.getNickName());
        returnedAccountDTO.setEmail(accountDTO.getEmail());
        returnedAccountDTO.setPassword(accountDTO.getPassword());
        when(accountService.saveAccountByDTO(anyLong(),any(AccountDTO.class))).thenReturn(returnedAccountDTO);
        mockMvc.perform(put(AccountController.BASE_URL+"/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(accountDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",equalTo("Demo")))
                .andExpect(jsonPath("$.address",equalTo("aa/bb/cc")));
    }

    @Test
    void removeAccount() throws Exception {
        mockMvc.perform(delete(AccountController.BASE_URL+"/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isGone());
        verify(accountService).deleteAccountById(anyLong());
    }
}
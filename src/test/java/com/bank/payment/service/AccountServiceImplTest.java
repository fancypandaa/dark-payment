package com.bank.payment.service;

import com.bank.payment.dto.mapper.AccountMapper;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.then;

import com.bank.payment.dto.model.AccountDTO;
import com.bank.payment.dto.model.AccountListDTO;
import com.bank.payment.domain.Account;
import com.bank.payment.repositories.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.hamcrest.core.IsEqual.equalTo;
class AccountServiceImplTest {
    @Mock
    AccountRepository accountRepository;
    AccountService accountService;
    public static final String NAME_1 = "My Account";
    public static final long ID_1 = 1L;
    public static final String NAME_2 = "My Account 2";
    public static final long ID_2 = 2L;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        accountService  = new AccountServiceImpl(AccountMapper.INSTANCE,accountRepository);
    }

    @Test
    void getAllAccounts() {
        List<Account> accounts =Arrays.asList(getAccount1(),getAccount2());
        given(accountRepository.findAll()).willReturn(accounts);
        AccountListDTO accountListDTO = accountService.getAllAccounts();
        then(accountRepository).should(times(1)).findAll();
        assertThat(accountListDTO.getAccounts().size(),is(equalTo(2)));
    }

    @Test
    void getAccountsById() {
        Account account = getAccount1();
        given(accountRepository.findById(anyLong())).willReturn(Optional.of(account));
        AccountDTO accountDTO = accountService.getAccountsById(1L);
        then(accountRepository).should(times(1)).findById(anyLong());
    }

    @Test
    void createNewAccount() {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setNickName(NAME_2);
        Account account = getAccount2();
        given(accountRepository.save(any(Account.class))).willReturn(account);
        AccountDTO savedAccount = accountService.createNewAccount(accountDTO);
        then(accountRepository).should().save(any(Account.class));
        assertThat(savedAccount.getNickName(),containsString(NAME_2));
    }

    @Test
    void saveAccountByDTO() {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setNickName(NAME_1);
        Account account = getAccount1();
        given(accountRepository.save(any(Account.class))).willReturn(account);
        AccountDTO savedAccount= accountService.saveAccountByDTO(ID_1,accountDTO);
        then(accountRepository).should().save(any(Account.class));
        assertThat(savedAccount.getNickName(),containsString(NAME_1));
    }

    @Test
    void patchAccount() {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setNickName(NAME_1);
        Account account = getAccount1();
        given(accountRepository.findById(anyLong())).willReturn(Optional.of(account));
        given(accountRepository.save(any(Account.class))).willReturn(account);
        AccountDTO savedAccount= accountService.patchAccount(ID_1,accountDTO);
        then(accountRepository).should().save(any(Account.class));
        then(accountRepository).should(times(1)).findById(anyLong());
//        assertThat(savedAccount.getName());

    }

    @Test
    void deleteAccountById() {
        accountService.deleteAccountById(ID_2);
        then(accountRepository).should().deleteById(ID_2);
    }

    private Account getAccount1() {
        Account account = new Account();
        account.setNickName(NAME_1);
        account.setId(ID_1);
        return account;
    }

    private Account getAccount2() {
        Account account = new Account();
        account.setNickName(NAME_2);
        account.setId(ID_2);
        return account;
    }
}
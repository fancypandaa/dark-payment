package com.bank.payment.api.mapper;

import com.bank.payment.api.model.AccountDTO;
import com.bank.payment.domain.Account;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class AccountMapperTest {
    private static final Long ID = 1L;
    private static final String NAME= "Test";
    private static final String PASSWORD="12345678";
    private static final String PEN_CODE="1234";
    private static final String ADDRESS="AAA-BBB-CCC-D";
    private static final Integer PHONE_NO=123456789;
    AccountMapper accountMapper = AccountMapper.INSTANCE;


    @Test
    void accountToAccountDTO() {
        Account account = new Account();
        account.setId(ID); account.setName(NAME);
        account.setPassword(PASSWORD); account.setPenCode(PEN_CODE);
        account.setAddress(ADDRESS); account.setPhoneNo(PHONE_NO);
        AccountDTO accountDTO = accountMapper.accountToAccountDTO(account);
        assertEquals(Long.valueOf(ID),accountDTO.getId());
        assertEquals(NAME,accountDTO.getName());
        assertNotNull(accountDTO);

    }

    @Test
    void accountDtoToAccount() {
        AccountDTO accountDto = new AccountDTO();
        accountDto.setId(ID); accountDto.setName(NAME);
        accountDto.setPassword(PASSWORD); accountDto.setPenCode(PEN_CODE);
        accountDto.setAddress(ADDRESS); accountDto.setPhoneNo(PHONE_NO);
        Account account = accountMapper.accountDtoToAccount(accountDto);
        assertNotNull(account);
        assertEquals(Long.valueOf(ID),account.getId());
        assertEquals(PASSWORD,account.getPassword());
        assertEquals(PEN_CODE,account.getPenCode());
        assertEquals(PHONE_NO,account.getPhoneNo());
    }
}
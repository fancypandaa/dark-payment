package com.bank.payment.api.mapper;

import com.bank.payment.dto.mapper.AccountMapper;
import com.bank.payment.dto.model.AccountDTO;
import com.bank.payment.domain.Account;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class AccountMapperTest {
    private static final Long ID = 1L;
    private static final String NAME= "Test";
    private static final String PASSWORD="12345678";
    private static final String SECURITY_CODE ="1234";
    private static final String EMAIL ="AAA-BBB-CCC-D";
    private static final String REF_CODE ="123456789";
    AccountMapper accountMapper = AccountMapper.INSTANCE;


    @Test
    void accountToAccountDTO() {
        Account account = new Account();
        account.setId(ID); account.setNickName(NAME);
        account.setPassword(PASSWORD); account.setSecurityCode(SECURITY_CODE);
        account.setEmail(EMAIL); account.setRef_Code(REF_CODE);
        AccountDTO accountDTO = accountMapper.accountToAccountDTO(account);
        assertEquals(Long.valueOf(ID),accountDTO.getId());
        assertEquals(NAME,accountDTO.getNickName());
        assertNotNull(accountDTO);

    }

    @Test
    void accountDtoToAccount() {
        AccountDTO accountDto = new AccountDTO();
        accountDto.setId(ID); accountDto.setNickName(NAME);
        accountDto.setPassword(PASSWORD); accountDto.setSecurityCode(SECURITY_CODE);
        accountDto.setEmail(EMAIL); accountDto.setRef_Code(REF_CODE);
        Account account = accountMapper.accountDtoToAccount(accountDto);
        assertNotNull(account);
        assertEquals(Long.valueOf(ID),account.getId());
        assertEquals(PASSWORD,account.getPassword());
        assertEquals(SECURITY_CODE,account.getSecurityCode());
        assertEquals(REF_CODE,account.getRef_Code());
    }
}
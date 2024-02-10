package com.bank.payment.service;
import com.bank.payment.api.model.AccountDTO;
import com.bank.payment.api.model.AccountListDTO;


public interface AccountService {
    AccountListDTO getAllAccounts();
    AccountDTO getAccountsById(Long id);
    AccountDTO createNewAccount(AccountDTO accountDTO);
    AccountDTO saveAccountByDTO(Long id,AccountDTO accountDTO);
    AccountDTO patchAccount(Long id, AccountDTO accountDTO);
    void deleteAccountById(Long id);
}

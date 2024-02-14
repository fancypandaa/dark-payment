package com.bank.payment.service;

import com.bank.payment.api.mapper.AccountMapper;
import com.bank.payment.api.model.AccountDTO;
import com.bank.payment.api.model.AccountListDTO;
import com.bank.payment.domain.Account;
import com.bank.payment.repositories.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService{
    private static final Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);

    private final AccountMapper accountMapper;
    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountMapper accountMapper, AccountRepository accountRepository) {
        this.accountMapper = accountMapper;
        this.accountRepository = accountRepository;
    }


    @Override
    public AccountListDTO getAllAccounts() {

        List<AccountDTO> accountDTOS= accountRepository.findAll()
                .stream().map(account -> {
                    AccountDTO accountDTO = accountMapper.accountToAccountDTO(account);
                    return accountDTO;
                }).collect(Collectors.toList());
        return new AccountListDTO(accountDTOS);
    }

    @Override
    public AccountDTO getAccountsById(Long id) {
        return accountRepository.findById(id)
                .map(accountMapper::accountToAccountDTO).orElseThrow(ResourceNotFoundException::new);
    }
    private AccountDTO saveAndReturnAccountDTO(Account account){
        Account newAccount = accountRepository.save(account);
        return accountMapper.accountToAccountDTO(newAccount);
    }
    @Override
    public AccountDTO createNewAccount(AccountDTO accountDTO) {
        return saveAndReturnAccountDTO(accountMapper.accountDtoToAccount(accountDTO));
    }

    @Override
    public AccountDTO saveAccountByDTO(Long id, AccountDTO accountDTO) {
        Account account = accountMapper.accountDtoToAccount(accountDTO);
        account.setId(id);
        return saveAndReturnAccountDTO(account);
    }

    @Override
    public AccountDTO patchAccount(Long id, AccountDTO accountDTO) {
        return accountRepository.findById(id).map(account -> {
            if(accountDTO.getNickName() != null ){
                account.setNickName(accountDTO.getNickName());
            }
            if(accountDTO.getEmail() != null){
                account.setEmail(accountDTO.getEmail());
            }
           return saveAndReturnAccountDTO(account);
        }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteAccountById(Long id) {
        accountRepository.deleteById(id);
    }
}

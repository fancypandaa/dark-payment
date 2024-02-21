package com.bank.payment.controller;

import com.bank.payment.dto.model.AccountDTO;
import com.bank.payment.dto.model.AccountListDTO;
import com.bank.payment.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AccountController.BASE_URL)
public class AccountController {
    public static final String BASE_URL = "/api/accounts";
    private final AccountService accountService;
    public AccountController(AccountService accountService){
        this.accountService = accountService;
    }
    @GetMapping
    public ResponseEntity<AccountListDTO> getAllAccounts(){
        AccountListDTO accountListDTO= accountService.getAllAccounts();
        return new ResponseEntity<>(accountListDTO,HttpStatus.OK);
    }
    @GetMapping({"/{id}"})
    public ResponseEntity<AccountDTO> getAccount(@PathVariable Long id){
        AccountDTO accountDTO= accountService.getAccountsById(id);
        return new ResponseEntity<>(accountDTO,HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<AccountDTO> postAccount(@RequestBody AccountDTO accountDTO){
        AccountDTO accountDTO1 = accountService.createNewAccount(accountDTO);
        return new ResponseEntity<>(accountDTO1,HttpStatus.CREATED);
    }
    @PatchMapping({"/{id}"})
    public ResponseEntity<AccountDTO> patchAccount(@PathVariable Long id,@RequestBody AccountDTO accountDTO){
        AccountDTO patchedAccount = accountService.patchAccount(id,accountDTO);
        return new ResponseEntity<>(patchedAccount,HttpStatus.OK);
    }
    @PutMapping({"/{id}"})
    public ResponseEntity<AccountDTO> updateAccount(@PathVariable Long id,@RequestBody AccountDTO accountDTO){
        AccountDTO updatedAccount = accountService.saveAccountByDTO(id,accountDTO);
        return new ResponseEntity<>(updatedAccount,HttpStatus.OK);
    }
    @DeleteMapping({"/{id}"})
    public ResponseEntity<HttpStatus> removeAccount(@PathVariable Long id){
        accountService.deleteAccountById(id);
        return new ResponseEntity<>(HttpStatus.GONE);
    }
}

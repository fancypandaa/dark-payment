package com.bank.payment.controller;

import com.bank.payment.api.model.BalanceDTO;
import com.bank.payment.service.BalanceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
@RestController
@RequestMapping(BalanceController.BASE_URI)
public class BalanceController {
    public static final String BASE_URI = "/api/balance";
    private BalanceService balanceService;

    public BalanceController(BalanceService balanceService) {
        this.balanceService = balanceService;
    }
    @GetMapping({"/account/{accountId}"})
    public ResponseEntity<List<BalanceDTO>> getListBalanceByAccountId(@PathVariable Long accountId){
        List<BalanceDTO> balanceDTO= balanceService.findBalanceByAccountId(accountId);
        return new ResponseEntity<>(balanceDTO, HttpStatus.OK);
    }
    @GetMapping({"/{id}"})
    public ResponseEntity<BalanceDTO> getBalance(@PathVariable Long id){
        BalanceDTO balanceDTO= balanceService.findBalanceById(id);
        return new ResponseEntity<>(balanceDTO,HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<BalanceDTO> postBalance(@RequestBody BalanceDTO balanceDTO){
        BalanceDTO balanceDTO1=balanceService.createNewBalance(balanceDTO);
        return new ResponseEntity<>(balanceDTO1,HttpStatus.CREATED);
    }

    @PutMapping({"/{id}"})
    public ResponseEntity<BalanceDTO> putBalance(@PathVariable Long id,@RequestBody BalanceDTO balanceDTO){
        BalanceDTO balanceDTO1= balanceService.saveBalanceByDTO(id,balanceDTO);
        return new ResponseEntity<>(balanceDTO1,HttpStatus.OK);
    }
    @PatchMapping({"/{id}"})
    public ResponseEntity<BalanceDTO> patchBalance(@PathVariable Long id,@RequestBody BalanceDTO balanceDTO){
        BalanceDTO balanceDTO1 = balanceService.patchBalance(id,balanceDTO);
        return new ResponseEntity<>(balanceDTO1,HttpStatus.OK);
    }
    @DeleteMapping({"/{id}"})
    public ResponseEntity<BalanceDTO> deleteBalance(@PathVariable Long id){
        balanceService.removeBalanceById(id);
        return new ResponseEntity<>(HttpStatus.GONE);
    }
}

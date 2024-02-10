package com.bank.payment.controller;

import com.bank.payment.api.model.BalanceLogsDTO;
import com.bank.payment.service.BalanceLogsService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController
@RequestMapping(BalanceLogsController.BASE_URI)
public class BalanceLogsController {
    public static final String BASE_URI="/api/logs";
    private final BalanceLogsService balanceLogsService;

    public BalanceLogsController(BalanceLogsService balanceLogsService) {
        this.balanceLogsService = balanceLogsService;
    }
    @GetMapping
    public ResponseEntity<Page<BalanceLogsDTO>> getLogsList(@RequestParam Optional<Integer> page, @RequestParam Optional<Integer>  size){
        Page<BalanceLogsDTO> balanceLogsDTOPage;
        if(page.isPresent()&&size.isPresent()){
            balanceLogsDTOPage = balanceLogsService.getAllBalanceLogs(page.get(), size.get());
        }
        else {
            balanceLogsDTOPage = balanceLogsService.getAllBalanceLogs(0,10);
        }
        return new ResponseEntity<>(balanceLogsDTOPage, HttpStatus.OK);
    }
    @GetMapping({"/{id}"})
    public ResponseEntity<BalanceLogsDTO> getBalanceLogs(@PathVariable UUID id){
        BalanceLogsDTO balanceLogsDTO= balanceLogsService.getBalanceLogById(id);
        return new ResponseEntity<>(balanceLogsDTO,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BalanceLogsDTO> postBalanceLogs(@RequestBody BalanceLogsDTO balanceLogsDTO){
        BalanceLogsDTO balanceLogsDTO1= balanceLogsService.createBalanceLogs(balanceLogsDTO);
        return new ResponseEntity<>(balanceLogsDTO1,HttpStatus.CREATED);
    }
    @PutMapping({"/{id}"})
    public ResponseEntity<BalanceLogsDTO> putBalanceLogs(@PathVariable UUID id,@RequestBody BalanceLogsDTO balanceLogsDTO){
        BalanceLogsDTO balanceLogsDTO1= balanceLogsService.saveBalanceLogsByDTO(id,balanceLogsDTO);
        return new ResponseEntity<>(balanceLogsDTO1,HttpStatus.OK);
    }

    @DeleteMapping({"/{id}"})
    public ResponseEntity<Void> deleteBalanceLogs(@PathVariable UUID id){
        balanceLogsService.removeBalanceLogsById(id);
        return new ResponseEntity<>(HttpStatus.GONE);
    }
}

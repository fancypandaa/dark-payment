package com.bank.payment.controller;

import com.bank.payment.dto.model.AccountDTO;
import com.bank.payment.dto.model.AuctionLogsDTO;
import com.bank.payment.service.AuctionLogsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping(AuctionLogsController.BASE_URL)
public class AuctionLogsController {
    public static final String BASE_URL = "/api/auctionsLogs";
    private final AuctionLogsService auctionLogsService;

    public AuctionLogsController(AuctionLogsService auctionLogsService) {
        this.auctionLogsService = auctionLogsService;
    }
    @GetMapping
    public ResponseEntity<List<AuctionLogsDTO>> getAllAuctionsLogs(){
        List<AuctionLogsDTO> auctionLogsDTOList= auctionLogsService.getAllAuctionLogs();
        return new ResponseEntity<>(auctionLogsDTOList, HttpStatus.OK);
    }

    @GetMapping({"/{id}"})
    public ResponseEntity<AuctionLogsDTO> getAuctionsLogsById(@PathVariable Long id){
        AuctionLogsDTO auctionLogsDTO = auctionLogsService.getAuctionLogById(id);
        return new ResponseEntity<>(auctionLogsDTO, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<AuctionLogsDTO> postAuctionLog(@RequestBody AuctionLogsDTO auctionLogsDTO){
        AuctionLogsDTO auctionLogsDTOI = auctionLogsService.createNewAuctionLog(auctionLogsDTO);
        return new ResponseEntity<>(auctionLogsDTOI,HttpStatus.CREATED);
    }
    @DeleteMapping({"/{id}"})
    public ResponseEntity<HttpStatus> removeAuctionLog(@PathVariable Long id){
        auctionLogsService.deleteLogById(id);
        return new ResponseEntity<>(HttpStatus.GONE);
    }
}

package com.bank.payment.service;

import com.bank.payment.domain.AuctionLogs;
import com.bank.payment.dto.mapper.AuctionLogsMapper;
import com.bank.payment.dto.model.AuctionLogsDTO;
import com.bank.payment.repositories.AuctionLogsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AuctionLogsServiceImpl implements AuctionLogsService{
    private static final Logger log = LoggerFactory.getLogger(AuctionLogsServiceImpl.class);
    private final AuctionLogsMapper auctionLogsMapper;
    private final AuctionLogsRepository auctionLogsRepository;

    public AuctionLogsServiceImpl(AuctionLogsMapper auctionLogsMapper, AuctionLogsRepository auctionLogsRepository) {
        this.auctionLogsMapper = auctionLogsMapper;
        this.auctionLogsRepository = auctionLogsRepository;
    }

    @Override
    public List<AuctionLogsDTO> getAllAuctionLogs() {
        List<AuctionLogsDTO> auctionLogsDTOList = auctionLogsRepository.findAll()
                .stream().map(auctionLogs -> {
                    return auctionLogsMapper.auctionLogsToAuctionLogsDTO(auctionLogs);
                }).collect(Collectors.toList());
        return auctionLogsDTOList;
    }

    @Override
    public AuctionLogsDTO getAuctionLogById(Long id) {
        Optional<AuctionLogs> auctionLogsOptional = auctionLogsRepository.findById(id);
        if(!auctionLogsOptional.isPresent()){
            return null;
        }
        AuctionLogsDTO auctionLogsDTO =auctionLogsMapper.
                auctionLogsToAuctionLogsDTO(auctionLogsOptional.get());
        return auctionLogsDTO;
    }

    @Override
    public AuctionLogsDTO createNewAuctionLog(AuctionLogsDTO auctionLogsDTO) {
        AuctionLogs auctionLogs = auctionLogsMapper.auctionLogsDTOToAuctionLogs(auctionLogsDTO);
         return auctionLogsMapper.auctionLogsToAuctionLogsDTO(auctionLogsRepository.save(auctionLogs));
    }

    @Override
    public void deleteLogById(Long id) {
        auctionLogsRepository.deleteById(id);
    }
}

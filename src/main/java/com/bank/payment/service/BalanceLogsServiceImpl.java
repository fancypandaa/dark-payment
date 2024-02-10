package com.bank.payment.service;

import com.bank.payment.api.mapper.BalanceLogsMapper;
import com.bank.payment.api.model.BalanceLogsDTO;
import com.bank.payment.api.model.BalanceLogsListDTO;
import com.bank.payment.domain.BalanceLogs;
import com.bank.payment.repositories.BalanceLogsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
@Service
public class BalanceLogsServiceImpl implements BalanceLogsService{
    private final BalanceLogsRepository balanceLogsRepository;
    private final BalanceLogsMapper balanceLogsMapper;
    private static final Logger log = LoggerFactory.getLogger(BalanceLogsServiceImpl.class);

    public BalanceLogsServiceImpl(BalanceLogsRepository balanceLogsRepository, BalanceLogsMapper balanceLogsMapper) {
        this.balanceLogsRepository = balanceLogsRepository;
        this.balanceLogsMapper = balanceLogsMapper;
    }

    @Override
    public Page<BalanceLogsDTO> getAllBalanceLogs(int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        List<BalanceLogsDTO> balanceLogsDTOList = balanceLogsRepository.findAll(pageable)
                .stream().map(balanceLogsMapper::balanceLogsToBalanceLogsDTO).collect(Collectors.toList());
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), balanceLogsDTOList.size());
        List<BalanceLogsDTO> balanceLogsDTOList1 = balanceLogsDTOList.subList(start,end);
        return new PageImpl<>(balanceLogsDTOList1,pageable,balanceLogsDTOList1.size());
    }

    @Override
    public BalanceLogsDTO getBalanceLogById(UUID id) {
        return balanceLogsRepository.findById(id).map(balanceLogsMapper::balanceLogsToBalanceLogsDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public BalanceLogsListDTO findBalanceLogsByBalanceId(Long balanceId) {
        List<BalanceLogsDTO> balanceLogsDTOList =balanceLogsRepository.findBalanceLogsByBalanceId(balanceId)
                .stream().map(balanceLogsMapper::balanceLogsToBalanceLogsDTO).collect(Collectors.toList());
        return new BalanceLogsListDTO(balanceLogsDTOList);
    }
    private BalanceLogsDTO saveAndReturnBalanceLogsDTO(BalanceLogs balanceLogs){
        BalanceLogs newBalance = balanceLogsRepository.save(balanceLogs);
        return balanceLogsMapper.balanceLogsToBalanceLogsDTO(newBalance);
    }
    @Override
    public BalanceLogsDTO createBalanceLogs(BalanceLogsDTO balanceLogsDTO) {
        return saveAndReturnBalanceLogsDTO(balanceLogsMapper.balanceLogsDtoToBalanceLogs(balanceLogsDTO));
    }

    @Override
    public BalanceLogsDTO saveBalanceLogsByDTO(UUID id, BalanceLogsDTO balanceLogsDTO) {
        BalanceLogs balanceLogs = balanceLogsMapper.balanceLogsDtoToBalanceLogs(balanceLogsDTO);
        balanceLogs.setId(id);
        return saveAndReturnBalanceLogsDTO(balanceLogs);
    }

    @Override
    public void removeBalanceLogsById(UUID id) {
        balanceLogsRepository.deleteById(id);
    }
}

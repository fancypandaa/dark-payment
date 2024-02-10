package com.bank.payment.api.mapper;

import com.bank.payment.api.model.BalanceLogsDTO;
import com.bank.payment.domain.BalanceLogs;
import com.bank.payment.domain.Operations;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class BalanceLogsMapperTest {
    private static final UUID ID= new UUID(99,1000);
    private static final Operations OPE=Operations.ADD_POINTS;
    private static final Boolean STATUS=Boolean.TRUE;
    private static final Date CREATED_AT= new Date();
    private static final Date UPDATED_AT=new Date();

    BalanceLogsMapper balanceLogsMapper = BalanceLogsMapper.INSTANCE;

    @Test
    void balanceLogsToBalanceLogsDTO() {
        BalanceLogs balanceLogs= new BalanceLogs();
        balanceLogs.setId(ID);
        balanceLogs.setOperation(OPE); balanceLogs.setOperationStatus(STATUS);
        balanceLogs.setCreatedAt(CREATED_AT); balanceLogs.setUpdatedAt(UPDATED_AT);
        BalanceLogsDTO balanceLogsDTO = balanceLogsMapper.balanceLogsToBalanceLogsDTO(balanceLogs);
        assertNotNull(balanceLogsDTO);
        assertEquals(ID,balanceLogsDTO.getId());
        assertEquals(OPE,balanceLogsDTO.getOperation());
        assertEquals(STATUS,balanceLogsDTO.getOperationStatus());
    }

    @Test
    void balanceLogsDtoToBalanceLogs() {

        BalanceLogsDTO balanceLogsDto= new BalanceLogsDTO();
        balanceLogsDto.setId(ID);
        balanceLogsDto.setOperation(OPE); balanceLogsDto.setOperationStatus(STATUS);
        balanceLogsDto.setCreatedAt(CREATED_AT); balanceLogsDto.setUpdatedAt(UPDATED_AT);
        BalanceLogs balanceLogs = balanceLogsMapper.balanceLogsDtoToBalanceLogs(balanceLogsDto);
        assertNotNull(balanceLogs);
        assertEquals(ID,balanceLogs.getId());
        assertEquals(UPDATED_AT,balanceLogs.getUpdatedAt());
        assertEquals(CREATED_AT,balanceLogs.getCreatedAt());
    }
}
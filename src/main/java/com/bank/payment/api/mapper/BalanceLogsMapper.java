package com.bank.payment.api.mapper;

import com.bank.payment.api.model.BalanceLogsDTO;
import com.bank.payment.domain.BalanceLogs;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BalanceLogsMapper {
    BalanceLogsMapper INSTANCE = Mappers.getMapper(BalanceLogsMapper.class);
    BalanceLogsDTO balanceLogsToBalanceLogsDTO(BalanceLogs balanceLogs);
    BalanceLogs balanceLogsDtoToBalanceLogs(BalanceLogsDTO balanceLogsDTO);
}

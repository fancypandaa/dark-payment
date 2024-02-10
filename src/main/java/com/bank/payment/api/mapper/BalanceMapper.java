package com.bank.payment.api.mapper;

import com.bank.payment.api.model.BalanceDTO;
import com.bank.payment.domain.Balance;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BalanceMapper {
    BalanceMapper INSTANCE = Mappers.getMapper(BalanceMapper.class);
    BalanceDTO balanceToBalanceDTO(Balance balance);
    Balance balanceDtoToBalance(BalanceDTO balanceDTO);
}

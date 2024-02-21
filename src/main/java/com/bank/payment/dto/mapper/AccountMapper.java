package com.bank.payment.dto.mapper;

import com.bank.payment.dto.model.AccountDTO;
import com.bank.payment.domain.Account;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AccountMapper {
    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);
    AccountDTO accountToAccountDTO(Account account);
    Account accountDtoToAccount(AccountDTO accountDTO);
}

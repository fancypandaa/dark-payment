package com.bank.payment.api.mapper;

import com.bank.payment.api.model.AuthDTO;
import com.bank.payment.domain.AuthUser;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthMapper {
    AuthMapper INSTANCE = Mappers.getMapper(AuthMapper.class);
    AuthDTO authToAuthDTO(AuthUser auth);

}

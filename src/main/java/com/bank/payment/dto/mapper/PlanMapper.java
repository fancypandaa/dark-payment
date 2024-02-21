package com.bank.payment.dto.mapper;

import com.bank.payment.dto.model.PlanDTO;
import com.bank.payment.domain.Plan;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PlanMapper {
    PlanMapper INSTANCE = Mappers.getMapper(PlanMapper.class);
    PlanDTO planToPlanDTO(Plan plan);
    Plan planDtoToPlan(PlanDTO planDTO);
}

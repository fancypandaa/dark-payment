package com.bank.payment.api.mapper;

import com.bank.payment.dto.mapper.PlanMapper;
import com.bank.payment.dto.model.PlanDTO;
import com.bank.payment.domain.BalanceTypes;
import com.bank.payment.domain.Plan;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlanMapperTest {
    private static final Long ID= 2L;
    private static final String NAME="PLAN-A";
    private static final Long MINIMUM=1000L;
    private static final Long MAXIMUM=50000L;
    private static final BalanceTypes TYPE=BalanceTypes.BLOCK_CHAIN;
    private static final double INTEREST= .05;
    private static final double FEES= 0.2;
    PlanMapper planMapper = PlanMapper.INSTANCE;

    @Test
    void planToPlanDTO() {
        Plan plan = new Plan();
        plan.setId(ID); plan.setName(NAME);
        plan.setType(TYPE);
        plan.setMinimum(MINIMUM); plan.setMaximum(MAXIMUM);
        plan.setFees(FEES); plan.setInterest(INTEREST);
        PlanDTO planDTO = planMapper.planToPlanDTO(plan);
        assertNotNull(planDTO);
        assertEquals(ID,planDTO.getId());
        assertEquals(NAME,planDTO.getName());
        assertEquals(TYPE,planDTO.getType());
    }

    @Test
    void planDtoToPlan() {

        PlanDTO planDto = new PlanDTO();
        planDto.setId(ID); planDto.setName(NAME);
        planDto.setType(TYPE);
        planDto.setMinimum(MINIMUM); planDto.setMaximum(MAXIMUM);
        planDto.setFees(FEES); planDto.setInterest(INTEREST);
        Plan plan = planMapper.planDtoToPlan(planDto);
        assertNotNull(plan);
        assertEquals(ID,plan.getId());
        assertEquals(MINIMUM,plan.getMinimum());
        assertEquals(MAXIMUM,plan.getMaximum());
    }
}
package com.bank.payment.service;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.then;

import com.bank.payment.api.mapper.PlanMapper;
import com.bank.payment.api.model.PlanDTO;
import com.bank.payment.domain.Plan;
import com.bank.payment.repositories.PlanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.*;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

class PlanServiceImplTest {
    @Mock
    PlanRepository planRepository;
    PlanService planService;
    public static final String NAME_1 = "PLAN B";
    public static final long ID_1 = 4L;
    public static final double FEE = 0.05;

    public static final String NAME_2 = "PLAN A";
    public static final long ID_2 = 5L;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        planService = new PlanServiceImpl(PlanMapper.INSTANCE,planRepository);
    }

    @Test
    void getPlansById() {
        Plan plan = getPlan1();
        given(planRepository.findById(anyLong())).willReturn(Optional.of(plan));
        PlanDTO planDTO = planService.getPlansById(ID_1);
        then(planRepository).should(times(1)).findById(anyLong());
    }

    @Test
    void createNewPlan() {
        PlanDTO planDTO = new PlanDTO();
        planDTO.setName(NAME_2);
        planDTO.setFees(FEE);
        Plan plan = getPlan2();
        given(planRepository.save(any(Plan.class))).willReturn(plan);
        PlanDTO savedPlan = planService.createNewPlan(planDTO);
        then(planRepository).should().save(any(Plan.class));
    }

    @Test
    void savePlanDTOByDTO() {
        PlanDTO planDTO = new PlanDTO();
        planDTO.setName(NAME_2);
        Plan plan = getPlan2();
        given(planRepository.save(any(Plan.class))).willReturn(plan);
        PlanDTO savedPlan = planService.savePlanDTOByDTO(ID_2,planDTO);
        then(planRepository).should().save(any(Plan.class));

    }

    @Test
    void patchAccount() {
        PlanDTO planDTO = new PlanDTO();
        planDTO.setName(NAME_1);
        planDTO.setFees(0.6);
        Plan plan = getPlan1();
        given(planRepository.findById(anyLong())).willReturn(Optional.of(plan));
        given(planRepository.save(any(Plan.class))).willReturn(plan);
        PlanDTO patchedPlan = planService.patchAccount(ID_1,planDTO);
        then(planRepository).should().save(any(Plan.class));
        then(planRepository).should(times(1)).findById(anyLong());
        assertEquals(patchedPlan.getFees(),planDTO.getFees());
    }

    @Test
    void deletePlanDTOById() {
        planService.deletePlanDTOById(ID_2);
        then(planRepository).should().deleteById(ID_2);
    }

    private Plan getPlan1() {
        Plan plan = new Plan();
        plan.setName(NAME_1);
        plan.setId(ID_1);
        return plan;
    }

    private Plan getPlan2() {
        Plan plan = new Plan();
        plan.setName(NAME_2);
        plan.setId(ID_2);
        return plan;
    }

}
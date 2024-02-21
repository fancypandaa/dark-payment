package com.bank.payment.controller;

import com.bank.payment.dto.model.PlanDTO;
import com.bank.payment.service.PlanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.bank.payment.controller.AbstractRestControllerTest.asJsonString;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
class PlanControllerTest {
    @Mock
    PlanService planService;
    @InjectMocks
    PlanController planController;
    MockMvc mockMvc;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(planController).setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }

    @Test
    void getPlanById() throws Exception{
        PlanDTO plan = new PlanDTO();
        plan.setName("Plan A");
        plan.setInterest(0.5);
        plan.setFees(0.02);
        when(planService.getPlansById(anyLong())).thenReturn(plan);
        mockMvc.perform(get(PlanController.BASE_URI+"/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",equalTo(plan.getName())));
    }

    @Test
    void createPlanByDto() throws Exception{
        PlanDTO plan = new PlanDTO();
        plan.setName("Plan A");
        plan.setInterest(0.5);
        plan.setFees(0.02);

        PlanDTO savedPlan = new PlanDTO();
        savedPlan.setName(plan.getName());
        savedPlan.setInterest(plan.getInterest());
        savedPlan.setFees(plan.getFees());
        when(planService.createNewPlan(plan)).thenReturn(savedPlan);
        mockMvc.perform(post(PlanController.BASE_URI)
                        .content(asJsonString(plan))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name",equalTo(plan.getName())))
                .andExpect(jsonPath("$.fees",equalTo(plan.getFees())));
    }

    @Test
    void updatePlan() throws Exception{
        PlanDTO plan = new PlanDTO();
        plan.setName("Plan A");
        plan.setInterest(0.5);
        plan.setFees(0.02);

        PlanDTO savedPlan = new PlanDTO();
        savedPlan.setName(plan.getName());
        savedPlan.setInterest(plan.getInterest());
        savedPlan.setFees(plan.getFees());
        when(planService.savePlanDTOByDTO(anyLong(),any(PlanDTO.class))).thenReturn(savedPlan);
        mockMvc.perform(put(PlanController.BASE_URI+"/1")
                        .content(asJsonString(plan))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",equalTo(savedPlan.getName())))
                .andExpect(jsonPath("$.fees",equalTo(savedPlan.getFees())));

    }

    @Test
    void patchPlan() throws Exception{
        PlanDTO plan = new PlanDTO();
        plan.setName("Plan A");


        PlanDTO savedPlan = new PlanDTO();
        savedPlan.setName(plan.getName());
        savedPlan.setFees(0.25);
        when(planService.patchAccount(anyLong(),any(PlanDTO.class))).thenReturn(savedPlan);
        mockMvc.perform(patch(PlanController.BASE_URI+"/1")
                        .content(asJsonString(plan))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",equalTo(savedPlan.getName())))
                .andExpect(jsonPath("$.fees",equalTo(savedPlan.getFees())));

    }

    @Test
    void deletedPlan() throws Exception{
        mockMvc.perform(delete(PlanController.BASE_URI+"/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isGone());
        verify(planService).deletePlanDTOById(anyLong());
    }
}
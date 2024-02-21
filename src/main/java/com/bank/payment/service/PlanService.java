package com.bank.payment.service;

import com.bank.payment.dto.model.PlanDTO;

public interface PlanService {
    PlanDTO getPlansById(Long id);
    PlanDTO createNewPlan(PlanDTO planDTO);
    PlanDTO savePlanDTOByDTO(Long id,PlanDTO planDTO);
    PlanDTO patchAccount(Long id, PlanDTO planDTO);
    void deletePlanDTOById(Long id);
}

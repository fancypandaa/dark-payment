package com.bank.payment.service;

import com.bank.payment.api.mapper.PlanMapper;
import com.bank.payment.api.model.PlanDTO;
import com.bank.payment.domain.Plan;
import com.bank.payment.repositories.PlanRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PlanServiceImpl implements PlanService{
    private final PlanMapper planMapper;
    private final PlanRepository planRepository;
    private static final Logger log = LoggerFactory.getLogger(PlanServiceImpl.class);

    public PlanServiceImpl(PlanMapper planMapper, PlanRepository planRepository) {
        this.planMapper = planMapper;
        this.planRepository = planRepository;
    }

    @Override
    public PlanDTO getPlansById(Long id) {
        return planRepository.findById(id).map(planMapper::planToPlanDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }
    private PlanDTO saveAndReturnAccountDTO( Plan plan ){
        Plan savedPlan  = planRepository.save(plan);
        return planMapper.planToPlanDTO(savedPlan);
    }
    @Override
    public PlanDTO createNewPlan(PlanDTO planDTO) {
        Plan plan = planMapper.planDtoToPlan(planDTO);
        return saveAndReturnAccountDTO(plan);
    }

    @Override
    public PlanDTO savePlanDTOByDTO(Long id, PlanDTO planDTO) {
        Plan plan = planMapper.planDtoToPlan(planDTO);
        plan.setId(id);
        return saveAndReturnAccountDTO(plan);
    }

    @Override
    public PlanDTO patchAccount(Long id, PlanDTO planDTO) {
        return planRepository.findById(id)
                .map(plan -> {
                    if(planDTO.getFees() != plan.getFees()){
                        plan.setFees(planDTO.getFees());
                    }
                    if(planDTO.getInterest() != plan.getInterest()){
                        plan.setInterest(planDTO.getInterest());
                    }
                    return saveAndReturnAccountDTO(plan);
                }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deletePlanDTOById(Long id) {
        planRepository.deleteById(id);
    }
}

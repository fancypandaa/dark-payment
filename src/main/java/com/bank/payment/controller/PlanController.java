package com.bank.payment.controller;

import com.bank.payment.api.model.AccountDTO;
import com.bank.payment.api.model.PlanDTO;
import com.bank.payment.service.PlanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(PlanController.BASE_URI)
public class PlanController {
    public static final String BASE_URI="/api/plans";
    private final PlanService planService;

    public PlanController(PlanService planService) {
        this.planService = planService;
    }
    @GetMapping({"/{id}"})
    public ResponseEntity<PlanDTO> getPlanById(@PathVariable Long id){
        PlanDTO planDTO = planService.getPlansById(id);
        return new ResponseEntity<>(planDTO, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<PlanDTO> createPlanByDto(@RequestBody PlanDTO planDTO){
        PlanDTO savedPlanDTO= planService.createNewPlan(planDTO);
        return new ResponseEntity<>(savedPlanDTO,HttpStatus.CREATED);
    }
    @PutMapping({"/{id}"})
    public ResponseEntity<PlanDTO> updatePlan(@PathVariable Long id,@RequestBody PlanDTO planDTO){
        PlanDTO updatedPlan= planService.savePlanDTOByDTO(id,planDTO);
        return new ResponseEntity<>(updatedPlan,HttpStatus.OK);
    }
    @PatchMapping({"/{id}"})
    public ResponseEntity<PlanDTO> patchPlan(@PathVariable Long id,@RequestBody PlanDTO planDTO){
        PlanDTO patchedPlan= planService.patchAccount(id,planDTO);
        return new ResponseEntity<>(patchedPlan,HttpStatus.OK);
    }
    @DeleteMapping({"/{id}"})
    public ResponseEntity<Void> deletedPlan(@PathVariable Long id){
        planService.deletePlanDTOById(id);
        return new ResponseEntity<>(HttpStatus.GONE);
    }
}

package com.bank.payment.controller;

import com.bank.payment.dto.model.BalanceDTO;
import com.bank.payment.domain.BalanceTypes;
import com.bank.payment.service.BalanceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.bank.payment.controller.AbstractRestControllerTest.asJsonString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.*;
class BalanceControllerTest {
    @Mock
    BalanceService balanceService;
    @InjectMocks
    BalanceController balanceController;
    MockMvc mockMvc;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(balanceController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler()).build();
    }

    @Test
    void getBalance() throws Exception{
        BalanceDTO balanceDTO = new BalanceDTO();
        balanceDTO.setLocalBalance(5000);
        balanceDTO.setBalance_State("Good");
        balanceDTO.setBalanceTypes(BalanceTypes.LOCAL_CURRENCY);
        BalanceDTO savedBalanceDto = new BalanceDTO();
        savedBalanceDto.setBalanceTypes(balanceDTO.getBalanceTypes());
        savedBalanceDto.setLocalBalance(balanceDTO.getLocalBalance());
        savedBalanceDto.setBalance_State(balanceDTO.getBalance_State());
        when(balanceService.findBalanceById(anyLong())).thenReturn(savedBalanceDto);
        mockMvc.perform(get(BalanceController.BASE_URI+"/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.localBalance",equalTo(5000.0)));
    }

    @Test
    void getListBalanceByAccountId()throws Exception {
        List<BalanceDTO> savedBalanceDto = new ArrayList<>(Arrays.asList(getBalanceDTO1(),getBalanceDTO2()));
        when(balanceService.findBalanceByAccountId(anyLong())).thenReturn(savedBalanceDto);
        mockMvc.perform(get(BalanceController.BASE_URI+"/account/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(2)));

    }

    @Test
    void postBalance() throws Exception{
        BalanceDTO balanceDTO = getBalanceDTO1();
        BalanceDTO savedBalance = new BalanceDTO();
        savedBalance.setBalance_State(balanceDTO.getBalance_State());
        savedBalance.setBalanceTypes(balanceDTO.getBalanceTypes());
        when(balanceService.createNewBalance(balanceDTO)).thenReturn(savedBalance);
        mockMvc.perform(post(BalanceController.BASE_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(balanceDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.balance_State",equalTo(savedBalance.getBalance_State())));
    }

    @Test
    void putBalance() throws Exception{
        BalanceDTO balanceDTO = getBalanceDTO1();
        BalanceDTO savedBalance = new BalanceDTO();
        savedBalance.setBalance_State(balanceDTO.getBalance_State());
        savedBalance.setBalanceTypes(balanceDTO.getBalanceTypes());
        when(balanceService.saveBalanceByDTO(anyLong(),any(BalanceDTO.class))).thenReturn(savedBalance);
        mockMvc.perform(put(BalanceController.BASE_URI+"/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(balanceDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance_State",equalTo(savedBalance.getBalance_State())));

    }

    @Test
    void patchBalance() throws Exception{
        BalanceDTO balanceDTO = getBalanceDTO1();
        BalanceDTO savedBalance = new BalanceDTO();
        savedBalance.setBalance_State("Bad");
        savedBalance.setBalanceTypes(balanceDTO.getBalanceTypes());
        when(balanceService.saveBalanceByDTO(anyLong(),any(BalanceDTO.class))).thenReturn(savedBalance);
        mockMvc.perform(patch(BalanceController.BASE_URI+"/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(balanceDTO)))
                .andExpect(status().isOk());

    }

    @Test
    void deleteBalance() throws Exception{
        mockMvc.perform(delete(BalanceController.BASE_URI+"/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isGone());
        verify(balanceService).removeBalanceById(anyLong());
    }
    private BalanceDTO getBalanceDTO1() {
        BalanceDTO balanceDTO = new BalanceDTO();
        balanceDTO.setBalance_State("Good");
        balanceDTO.setBalanceTypes(BalanceTypes.BLOCK_CHAIN);
        return balanceDTO;
    }
    private BalanceDTO getBalanceDTO2() {
        BalanceDTO balanceDTO = new BalanceDTO();
        balanceDTO.setBalance_State("Bad");
        balanceDTO.setBalanceTypes(BalanceTypes.LOCAL_CURRENCY);
        return balanceDTO;
    }
}
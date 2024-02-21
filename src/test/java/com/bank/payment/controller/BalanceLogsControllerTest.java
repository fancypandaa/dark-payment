package com.bank.payment.controller;

import com.bank.payment.dto.model.BalanceLogsDTO;
import com.bank.payment.domain.Operations;
import com.bank.payment.service.BalanceLogsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static com.bank.payment.controller.AbstractRestControllerTest.asJsonString;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class BalanceLogsControllerTest {
    @Mock
    BalanceLogsService balanceLogsService;
    @InjectMocks
    BalanceLogsController balanceLogsController;
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(balanceLogsController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler()).build();
    }


    @Test
    void getLogsList() throws Exception{
        Pageable pageable = PageRequest.of(0,2);
        List<BalanceLogsDTO> balanceLogsDTOList = new ArrayList<>(Arrays.asList(getBalanceLogsDTO1(),getBalanceLogsDTO2()));
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), balanceLogsDTOList.size());
        List<BalanceLogsDTO> balanceLogsDTOList1 = balanceLogsDTOList.subList(start,end);
        Page<BalanceLogsDTO> pag =new PageImpl<>(balanceLogsDTOList1,pageable,balanceLogsDTOList1.size());
        when(balanceLogsService.getAllBalanceLogs(0,2)).thenReturn(pag);
        mockMvc.perform(get(BalanceLogsController.BASE_URI)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getBalanceLogs() throws Exception{
        BalanceLogsDTO balanceLogsDTO= getBalanceLogsDTO1();
        BalanceLogsDTO savedlogs= new BalanceLogsDTO();

        savedlogs.setCreatedAt(balanceLogsDTO.getCreatedAt());
        savedlogs.setOperationStatus(balanceLogsDTO.getOperationStatus());
        when(balanceLogsService.getBalanceLogById(UUID.randomUUID())).thenReturn(savedlogs);
        mockMvc.perform(get(BalanceLogsController.BASE_URI+"/"+UUID.randomUUID()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void postBalanceLogs() throws Exception{
        BalanceLogsDTO balanceLogsDTO= getBalanceLogsDTO1();
        BalanceLogsDTO savedlogs= new BalanceLogsDTO();
        savedlogs.setCreatedAt(balanceLogsDTO.getCreatedAt());
        savedlogs.setOperationStatus(balanceLogsDTO.getOperationStatus());
        when(balanceLogsService.createBalanceLogs(balanceLogsDTO)).thenReturn(savedlogs);
        mockMvc.perform(post(BalanceLogsController.BASE_URI).
                        contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(balanceLogsDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    void putBalanceLogs()throws Exception {
        BalanceLogsDTO balanceLogsDTO= getBalanceLogsDTO1();
        BalanceLogsDTO savedlogs= new BalanceLogsDTO();
        savedlogs.setCreatedAt(balanceLogsDTO.getCreatedAt());
        savedlogs.setOperationStatus(balanceLogsDTO.getOperationStatus());
        when(balanceLogsService.saveBalanceLogsByDTO(any(UUID.class),any(BalanceLogsDTO.class))).thenReturn(savedlogs);
        mockMvc.perform(put(BalanceLogsController.BASE_URI+"/"+UUID.randomUUID()).
                        contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(balanceLogsDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void deleteBalanceLogs()throws Exception {
        mockMvc.perform(delete(BalanceLogsController.BASE_URI+"/"+UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isGone());
        verify(balanceLogsService).removeBalanceLogsById(any(UUID.class));
    }
    private BalanceLogsDTO getBalanceLogsDTO1() {
        BalanceLogsDTO balanceLogsDTO = new BalanceLogsDTO();
        balanceLogsDTO.setOperation(Operations.ADD_COINS);
        balanceLogsDTO.setCreatedAt(new Date());
        return balanceLogsDTO;
    }
    private BalanceLogsDTO getBalanceLogsDTO2() {
        BalanceLogsDTO balanceLogsDTO = new BalanceLogsDTO();
        balanceLogsDTO.setOperation(Operations.ADD_COINS);
        balanceLogsDTO.setCreatedAt(new Date());
        return balanceLogsDTO;
    }
}
package com.bank.payment.api.model;

import com.bank.payment.domain.Operations;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class BalanceLogsDTO {
    private UUID Id;
    private Operations operation;
    private Boolean OperationStatus;
    private Date createdAt= new Date();
    private Date updatedAt=new Date();
}

package com.bank.payment.dto.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;
@Data
@AllArgsConstructor
public class AccountListDTO {
    List<AccountDTO> accounts;
}

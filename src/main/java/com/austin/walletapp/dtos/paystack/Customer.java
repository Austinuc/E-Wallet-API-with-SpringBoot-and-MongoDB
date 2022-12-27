package com.austin.walletapp.dtos.paystack;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    private String first_name;
    private String last_name;
    private String email;
    private String customer_code;
    private String phone;
    private String risk_action;
}

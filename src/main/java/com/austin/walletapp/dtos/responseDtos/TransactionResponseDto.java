package com.austin.walletapp.dtos.responseDtos;

import com.austin.walletapp.dtos.paystack.Customer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponseDto {

    private String id;
    private String reference;
    private String domain;
    private String source;
    private String transactionType;
    private String status;
    private String message;
    private String description; //for webhook response
    private String request_code; //for webhook response
    private Customer customer; //for webhook response
    private boolean paid; //for webhook response
    private BigDecimal amount;
    private String gateway_response;
    private String channel;
    private String currency;
    private String ip_address;

    private String integration;
    private String reason;
    private String transfer_code;

}

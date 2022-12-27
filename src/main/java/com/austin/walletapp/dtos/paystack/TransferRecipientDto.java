package com.austin.walletapp.dtos.paystack;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class TransferRecipientDto {

    private String id;
    private boolean active;
    private String name;
    private String recipient_code;
    private String domain;
    private String integration;
    private String type;
    private Date createdAt;
    private Date updatedAt;
    private boolean is_deleted;
    private AccountDto details;

}

package com.austin.walletapp.dtos.paystack;

import com.austin.walletapp.utils.AppUtil;
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
public class BankDto {
    private String id;
    private String name;
    private String code;
    private boolean active;
    private String country;
    private String currency;
    private String type;
    private boolean is_deleted;

}

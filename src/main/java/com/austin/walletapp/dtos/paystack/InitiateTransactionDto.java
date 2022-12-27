package com.austin.walletapp.dtos.paystack;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class InitiateTransactionDto {
    @NotBlank(message = "email must be provided")
    private String email;
    @NotBlank(message = "amount must be provided and should be specified in Kobo")
    private String amount;

    private String callback_url;

    private Object metadata;
}

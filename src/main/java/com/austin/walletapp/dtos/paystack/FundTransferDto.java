package com.austin.walletapp.dtos.paystack;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class FundTransferDto {

    @Schema(example = "TRF_1a25w1h3n0xctjg")
    private String reference;
    @NotBlank
    @Schema(example = "austin5astro@gmail.com")
    private String email;
    @Schema(example = "30000")
    private BigDecimal amount;
    @Schema(example = "balance")
    private String source;
    @Schema(example = "RCP_1a25w1h3n0xctjg")
    private String recipient;
    @Schema(example = "For party")
    private String reason;

}

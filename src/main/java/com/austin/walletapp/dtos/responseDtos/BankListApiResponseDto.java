package com.austin.walletapp.dtos.responseDtos;

import com.austin.walletapp.dtos.paystack.BankDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankListApiResponseDto {
    private String message;
    private boolean status;
    private List<BankDto> data;
}

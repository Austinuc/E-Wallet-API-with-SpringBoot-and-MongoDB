package com.austin.walletapp.services;

import com.austin.walletapp.dtos.responseDtos.WalletResponseDto;

import java.math.BigDecimal;

public interface WalletServices {

    WalletResponseDto updateWallet(String eamil, BigDecimal amount);
}

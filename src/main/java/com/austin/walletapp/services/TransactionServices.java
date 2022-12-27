package com.austin.walletapp.services;

import com.austin.walletapp.dtos.responseDtos.TransactionResponseDto;

public interface TransactionServices {

    TransactionResponseDto logTransaction(TransactionResponseDto transactionResponseDto);
}

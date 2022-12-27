package com.austin.walletapp.services;


import com.austin.walletapp.dtos.paystack.*;
import com.austin.walletapp.dtos.responseDtos.ApiResponse;
import com.austin.walletapp.dtos.responseDtos.TransactionResponseDto;

import java.util.List;

public interface PaymentServices {

    ApiResponse<List<BankDto>> fetchBanks(String currency, String type);

    ApiResponse<String> getPaymentLink(InitiateTransactionDto transactionDto);

    ApiResponse<TransactionResponseDto> verifyTransaction(String paymentReference);

    ApiResponse<AccountDto> getAccountDetails(String accountNumber, String bankCode);

    ApiResponse<FundTransferDto> createTransferRecipient(AccountDto accountDto);

    ApiResponse<TransactionResponseDto> initiateTransfer(FundTransferDto fundTransferDto);
}

package com.austin.walletapp.controllers.consumers;

import com.austin.walletapp.dtos.paystack.*;
import com.austin.walletapp.dtos.responseDtos.ApiResponse;
import com.austin.walletapp.dtos.responseDtos.TransactionResponseDto;
import com.austin.walletapp.services.PaymentServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController {
    @Autowired
    private PaymentServices paymentServices;

    @Operation(summary = "Initiates a transaction to get a payment link")
    @PostMapping("/deposit/initiate")
    public ResponseEntity<ApiResponse> getPaymentUrl(@RequestBody InitiateTransactionDto transactionDto) {

        return ResponseEntity.ok(paymentServices.getPaymentLink(transactionDto));
    }

    @Operation(summary = "Webhook to listen for changes in any transaction status")
    @PostMapping("/status-webhook")
    public ResponseEntity<String> getPaymentStatus(@RequestBody TransactionResponseDto transactionResponseDto) {

        return ResponseEntity.ok().body("Thank you");
    }

    @Operation(summary = "Checks if a transaction was successful or not")
    @GetMapping("/verify/{payment_reference}")
    public ResponseEntity<ApiResponse<TransactionResponseDto>> confirmPayment(
            @Parameter(description = "Use the reference number generated when the transaction was initiated")
            @PathVariable String payment_reference) {

        return ResponseEntity.ok(paymentServices.verifyTransaction(payment_reference));
    }

//   ====================== Transfer transactions =====================

    @Operation(summary = "Confirm the account details before making transfers")
    @GetMapping("/validate-account-details")
    public ResponseEntity<ApiResponse<AccountDto>> validateAccountDetails(
            @Parameter(description = "10 digit account number") @RequestParam(name = "account_number") String accountNumber,
            @Parameter(description = "011 for first bank, 044 - access bank, etc") @RequestParam(name = "bank_code") String bankCode) {

        return ResponseEntity.ok().body(paymentServices.getAccountDetails(accountNumber, bankCode));
    }

    @Operation(summary = "Fetch list of supported bank that are active in a country")
    @GetMapping("/banks")
    public ResponseEntity<ApiResponse<List<BankDto>>> fetchAllBanks(@Parameter(description = "NGN for Nigeria, GHS for Ghana etc") @RequestParam(name = "currency") String currency,
                                                                    @Parameter(description = "(Optional) enter 'nuban' or 'mobile_money', etc") @RequestParam(name = "type", required = false) String type) {
        return ResponseEntity.ok(paymentServices.fetchBanks(currency, type != null && !type.equals("") ? type : null));
    }

    @Operation(summary = "Before sending money to an account, you need to create a transfer recipient with the customer’s account details.")
    @PostMapping("/transfer/create-transfer-recipient")
    public ResponseEntity<ApiResponse<FundTransferDto>> createTransferRecipient(@RequestBody AccountDto accountDto) {

        return ResponseEntity.ok(paymentServices.createTransferRecipient(accountDto));
    }

    @Operation(summary = "Initiate the transfer")
    @PostMapping("/transfer/initiate")
    public ResponseEntity<ApiResponse<TransactionResponseDto>> initiateTransfer(@RequestBody FundTransferDto fundTransferDto) {

        return ResponseEntity.ok(paymentServices.initiateTransfer(fundTransferDto));
    }
}

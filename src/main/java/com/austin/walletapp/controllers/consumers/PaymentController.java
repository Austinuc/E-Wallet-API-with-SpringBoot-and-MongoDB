package com.austin.walletapp.controllers.consumers;

import com.austin.walletapp.dtos.paystack.*;
import com.austin.walletapp.dtos.responseDtos.ApiResponse;
import com.austin.walletapp.dtos.responseDtos.TransactionResponseDto;
import com.austin.walletapp.services.PaymentServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/payment")
@Tag(name = "Payment Endpoint", description = "<h3>To deposit: </h3> " +
        "<ol>" +
        "<li>Go to '/deposit/initiate' endpoint and enter your details. The <b>callback</b> and <b>metadata</b> are optional. just leave them the way they are</li> " +
        "<li>Copy the payment link returned in the response object to any browser to make your payment.</li>" +
        "<li>Copy the reference code returned if it was successful and go to '/verify/{payment_reference}' endpoint to verify if your deposit was successful</li>" +
        "</ol> " +
        "<h3> For withdrawal which is implemented as transfer:</h3> " +
        "<ol> " +
        "<li>Go to 'withdrawal/create-transfer-recipient' endpoint to create you Transfer recipient</li> " +
        "<li>Copy the <b>reference</b> code 'TRF_....' and the <b>recipient</b> code 'RCP_....' generated and got to initiate withdrawal endpoint</li> " +
        "<li>After initiating your withdrawal, you will receive a error code response because the payment was a test payment</li>" +
        "</ol> " +
        "<p>You can use the <b>validate-account-details</b> endpoint to validate your account details and the " +
        "<b>banks</b> endpoint to see all available banks</p>")
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
    @PostMapping("/withdrawal/create-transfer-recipient")
    public ResponseEntity<ApiResponse<FundTransferDto>> createTransferRecipient(@RequestBody AccountDto accountDto) {

        return ResponseEntity.ok(paymentServices.createTransferRecipient(accountDto));
    }

    @Operation(summary = "Initiate the transfer")
    @PostMapping("/withdrawal/initiate")
    public ResponseEntity<ApiResponse<TransactionResponseDto>> initiateTransfer(@RequestBody FundTransferDto fundTransferDto) {

        return ResponseEntity.ok(paymentServices.initiateTransfer(fundTransferDto));
    }
}

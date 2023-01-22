package com.austin.walletapp.services.impl;

import com.austin.walletapp.dtos.paystack.*;
import com.austin.walletapp.dtos.responseDtos.ApiResponse;
import com.austin.walletapp.dtos.responseDtos.BankListApiResponseDto;
import com.austin.walletapp.dtos.responseDtos.TransactionResponseDto;
import com.austin.walletapp.enums.TransactionType;
import com.austin.walletapp.exceptions.InvalidTransactionException;
import com.austin.walletapp.exceptions.NotFoundException;
import com.austin.walletapp.models.Wallet;
import com.austin.walletapp.repositories.TransactionRepository;
import com.austin.walletapp.repositories.WalletRepository;
import com.austin.walletapp.services.PaymentServices;
import com.austin.walletapp.services.TransactionServices;
import com.austin.walletapp.services.WalletServices;
import com.austin.walletapp.utils.AppUtil;
import com.austin.walletapp.utils.LocalMemStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PaymentServicesImpl implements PaymentServices {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private TransactionServices transactionServices;
    @Autowired
    private LocalMemStorage localMemStorage;

    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private RestTemplate restTemplate;
    @Value("${paystack.secretkey}")
    private String apiKey;
    @Value("${paystack.initiateurl}")
    private String url;

    @Autowired
    private WalletServices walletService;
    @Autowired
    private AppUtil appUtil;


    @Override
    public ApiResponse<List<BankDto>> fetchBanks(String currency, String type) {
        String url = "https://api.paystack.co/bank?currency="+currency+((type == null)? "": "&type="+type);

        //Set authorization header for querying Paystack's end points
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer "+apiKey);

        HttpEntity entity = new HttpEntity<>(headers);

        appUtil.log("Fetching available banks");

        ResponseEntity<BankListApiResponseDto> apiResponse = restTemplate.exchange(url, HttpMethod.GET, entity, BankListApiResponseDto.class);

        List<BankDto> banks = Objects.requireNonNull(apiResponse.getBody()).getData().stream()
                .map((bank -> appUtil.getMapper().convertValue(bank, BankDto.class)))
                .filter(BankDto::isActive)
                .collect(Collectors.toList());

        return new ApiResponse<>(apiResponse.getBody().getMessage(), apiResponse.getBody().isStatus(), banks);
    }

    @Override
    public ApiResponse getPaymentLink(InitiateTransactionDto transactionDto) {
        transactionDto.setAmount(transactionDto.getAmount()+"00");
        //Set authorization header for querying Paystack's end points
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + apiKey);
        HttpEntity<InitiateTransactionDto> entity = new HttpEntity<>(transactionDto,headers);

        appUtil.log("Generating payment url for: "+transactionDto.getEmail());

        return restTemplate.exchange(url, HttpMethod.POST, entity, ApiResponse.class).getBody();
    }

    @Override
    public ApiResponse<TransactionResponseDto> verifyTransaction(String payment_reference) {

        String url = "https://api.paystack.co/transaction/verify/" + payment_reference;

        //Set authorization header for querying Paystack's end points
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + apiKey);
        HttpEntity entity = new HttpEntity<>(headers);

        ResponseEntity<ApiResponse> transactionResponseDto =
                restTemplate.exchange(url, HttpMethod.GET, entity, ApiResponse.class);

        TransactionResponseDto responseData = appUtil.getMapper().convertValue(Objects
                .requireNonNull(transactionResponseDto.getBody()).getData(), TransactionResponseDto.class);
        responseData.setAmount(responseData.getAmount().divide(new BigDecimal(100), 2, RoundingMode.DOWN));

        appUtil.print("Transaction verification data: "+transactionResponseDto.getBody());

        //if transaction is not logged, log it...
        if (!transactionRepository
                .existsByReferenceOrId(responseData.getReference(), responseData.getId())) {

            if (responseData.getStatus().equalsIgnoreCase("success")) { // Update wallet only when transaction is fulfilled
                walletService.updateWallet(responseData.getCustomer().getEmail(), responseData.getAmount());
            }
            //save transaction to DB for transaction history purposes
            responseData.setTransactionType(TransactionType.TRANSACTION_TYPE_DEPOSIT.getTransaction());
            transactionServices.logTransaction(responseData);
        }

        return new ApiResponse<>(responseData.getGateway_response(),
                responseData.getStatus().equalsIgnoreCase("success"),responseData);
    }

    @Override
    public ApiResponse<AccountDto> getAccountDetails(String accountNumber, String bankCode) {

        String endPoint = "https://api.paystack.co/bank/resolve?account_number="+accountNumber+"&bank_code="+bankCode;

        //Set authorization header for querying Paystack's end points
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + apiKey);
        HttpEntity entity = new HttpEntity<>(headers);

        //Retrieve account details from Paystack 'bank resolve' API
        ResponseEntity<ApiResponse> responseEntity =
                restTemplate.exchange(endPoint, HttpMethod.GET, entity, ApiResponse.class);

        //Map response data to AccountDto
        AccountDto accountDto = appUtil.getMapper().convertValue(Objects
                .requireNonNull(responseEntity.getBody()).getData(), AccountDto.class);

        return new ApiResponse<>(responseEntity.getBody().getMessage(), responseEntity.getBody().isStatus(), accountDto);
    }

    @Override
    public ApiResponse<FundTransferDto> createTransferRecipient(AccountDto accountDto) {
        String endPoint = "https://api.paystack.co/transferrecipient";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + apiKey);
        HttpEntity<AccountDto> entity = new HttpEntity<>(accountDto,headers);

        ResponseEntity<ApiResponse> apiResponse = restTemplate.exchange(endPoint, HttpMethod.POST, entity, ApiResponse.class);

        TransferRecipientDto transferRecipientDto = appUtil.getMapper()
                .convertValue(Objects.requireNonNull(apiResponse.getBody()).getData(), TransferRecipientDto.class);

        //Save a reference code to this transfer in memcache for use while initiating the transfer
        String transferUniqueReference = appUtil.generateSerialNumber("TRF_");
        localMemStorage.save(transferRecipientDto.getRecipient_code(), transferUniqueReference, 3600);

        FundTransferDto fundTransferDto = FundTransferDto.builder()
                .recipient(transferRecipientDto.getRecipient_code())
                .reference(transferUniqueReference)
                .build();

        return new ApiResponse<>(apiResponse.getBody().getMessage(), apiResponse.getBody().isStatus(), fundTransferDto);
    }

    @Override
    public ApiResponse<TransactionResponseDto> initiateTransfer(FundTransferDto fundTransferDto) {
        if (!localMemStorage.keyExist(fundTransferDto.getRecipient())) {
            throw new InvalidTransactionException("Transfer session has expired please try again");
        }
        BigDecimal balance = walletRepository.findByEmail(fundTransferDto.getEmail()).map(Wallet::getBalance).orElse(BigDecimal.ZERO);
        if (balance.compareTo(fundTransferDto.getAmount()) < 0)
            throw new InvalidTransactionException("Insufficient balance");

        localMemStorage.clear(fundTransferDto.getRecipient());

        String endPoint = "https://api.paystack.co/transfer";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + apiKey);
        HttpEntity<FundTransferDto> entity = new HttpEntity<>(fundTransferDto,headers);
        ResponseEntity<ApiResponse> apiResponse = null;
        try {

            apiResponse = restTemplate.exchange(endPoint, HttpMethod.POST, entity, ApiResponse.class);

        } catch (Exception e) {
            throw  new NotFoundException(e.getMessage()+". Sorry about that. This is just a test API, but your transfer would be processed if this was a production app");
        }
        TransactionResponseDto transactionResponseDto = appUtil.getMapper()
                .convertValue(Objects.requireNonNull(apiResponse.getBody()).getData(), TransactionResponseDto.class);
        transactionResponseDto.setTransactionType(TransactionType.TRANSACTION_TYPE_TRANSFER.getTransaction());
        transactionResponseDto.setAmount(transactionResponseDto.getAmount().divide(new BigDecimal(100), 2, RoundingMode.DOWN));

        return new ApiResponse<>(apiResponse.getBody().getMessage(),
                apiResponse.getBody().isStatus(), transactionServices.logTransaction(transactionResponseDto));

    }

}

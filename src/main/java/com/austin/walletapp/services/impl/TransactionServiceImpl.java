package com.austin.walletapp.services.impl;

import com.austin.walletapp.dtos.responseDtos.TransactionResponseDto;
import com.austin.walletapp.models.Transactions;
import com.austin.walletapp.repositories.TransactionRepository;
import com.austin.walletapp.services.TransactionServices;
import com.austin.walletapp.utils.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionServices {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AppUtil appUtil;

    @Override
    public TransactionResponseDto logTransaction(TransactionResponseDto transactionResponseDto) {

        if (!transactionRepository
                .existsByReferenceOrId(transactionResponseDto.getReference(), transactionResponseDto.getId())) {

            Transactions transaction = appUtil.getMapper().convertValue(transactionResponseDto, Transactions.class);
            transaction.setCustomerEmail(transactionResponseDto.getCustomer().getEmail());
            transactionRepository.save(transaction);
        }

        return transactionResponseDto;
    }
}

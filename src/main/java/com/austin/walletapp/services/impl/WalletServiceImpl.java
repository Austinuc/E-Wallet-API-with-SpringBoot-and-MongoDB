package com.austin.walletapp.services.impl;

import com.austin.walletapp.dtos.responseDtos.WalletResponseDto;
import com.austin.walletapp.models.Wallet;
import com.austin.walletapp.repositories.WalletRepository;
import com.austin.walletapp.services.WalletServices;
import com.austin.walletapp.utils.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class WalletServiceImpl implements WalletServices {
    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private AppUtil appUtil;
    @Override
    public WalletResponseDto updateWallet(String email, BigDecimal amount) {

        Wallet wallet = walletRepository.findByEmail(email)
                .orElse(Wallet.builder()
                        .walletUUID(appUtil.generateSerialNumber("0"))
                        .balance(BigDecimal.ZERO)
                        .email(email)
                        .build());
        wallet.setBalance(wallet.getBalance().add(amount));
        wallet.setUpdatedAt();

        return appUtil.getMapper().convertValue(walletRepository.save(wallet), WalletResponseDto.class);
    }
}

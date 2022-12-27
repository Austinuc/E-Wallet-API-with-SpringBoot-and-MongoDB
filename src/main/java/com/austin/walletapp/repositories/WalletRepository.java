package com.austin.walletapp.repositories;

import com.austin.walletapp.models.Wallet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends MongoRepository<Wallet, String> {
    Optional<Wallet> findByEmail(String email);
}

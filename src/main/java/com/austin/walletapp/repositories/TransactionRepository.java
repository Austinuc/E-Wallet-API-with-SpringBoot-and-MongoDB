package com.austin.walletapp.repositories;

import com.austin.walletapp.models.Transactions;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionRepository extends MongoRepository<Transactions, String> {

    boolean existsByReferenceOrId(String reference, String id);
}

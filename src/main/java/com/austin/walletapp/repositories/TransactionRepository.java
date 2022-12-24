package com.austin.walletapp.repositories;

import com.austin.walletapp.models.Transactions;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends MongoRepository<Transactions, String> {
}

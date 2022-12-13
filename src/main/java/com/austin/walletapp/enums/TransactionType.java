package com.austin.walletapp.enums;

public enum TransactionType {

    TRANSACTION_TYPE_WITHDRAW("transaction:withdraw"),
    TRANSACTION_TYPE_DEPOSIT("transaction:deposit");
    private final String transaction;
    TransactionType(String transaction) {
        this.transaction = transaction;
    }

    public String getTransaction() {
        return transaction;
    }
}

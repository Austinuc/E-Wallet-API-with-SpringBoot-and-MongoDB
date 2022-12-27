package com.austin.walletapp.exceptions;


public class InvalidTransactionException extends RuntimeException {

    private String message;

    public InvalidTransactionException() {
        super();
        message = "Invalid transaction";
    }

    public InvalidTransactionException(String message) {
        super(message);
        this.message = message;
    }
}

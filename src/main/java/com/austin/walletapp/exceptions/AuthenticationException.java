package com.austin.walletapp.exceptions;

public class AuthenticationException extends RuntimeException {

    private String message;

    public AuthenticationException() {
        super();
        this.message = "Authentication failed";
    }
    public AuthenticationException(String msg) {
        super(msg);
        this.message = msg;
    }
}


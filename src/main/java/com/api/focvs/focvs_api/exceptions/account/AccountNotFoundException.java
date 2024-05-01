package com.api.focvs.focvs_api.exceptions.account;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException() {
        super();
    }

    public AccountNotFoundException(String message) {
        super(message);
    }
}

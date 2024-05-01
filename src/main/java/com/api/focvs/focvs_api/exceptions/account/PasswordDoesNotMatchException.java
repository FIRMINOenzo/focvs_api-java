package com.api.focvs.focvs_api.exceptions.account;

public class PasswordDoesNotMatchException extends RuntimeException {
    public PasswordDoesNotMatchException() {
        super();
    }

    public PasswordDoesNotMatchException(String message) {
        super(message);
    }
}

package com.api.focvs.focvs_api.exceptions.account;

public class EmailAlreadyInUseException extends RuntimeException {
    public EmailAlreadyInUseException() {
        super();
    }

    public EmailAlreadyInUseException(String message) {
        super(message);
    }
}

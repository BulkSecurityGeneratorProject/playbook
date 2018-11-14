package com.playbook.error.exception;


public class PasswordResetException extends RuntimeException {

    public PasswordResetException(String message) {
        super(message);
    }
}

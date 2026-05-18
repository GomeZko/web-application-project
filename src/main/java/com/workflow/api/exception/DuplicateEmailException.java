package com.workflow.api.exception;

public class DuplicateEmailException extends RuntimeException {

    public DuplicateEmailException(String email) {
        super("User with email '" + email + "' already exists");
    }
}

package com.ivarrace.gringotts.domain.exception;

public class UserAlreadyRegisteredException extends RuntimeException {

    public UserAlreadyRegisteredException(String username) {
        super(String.format("User [%s] already registered", username));
    }

}

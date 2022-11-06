package com.ivarrace.gringotts.application.exception;

public class UserAlreadyRegisteredException extends RuntimeException {

    public UserAlreadyRegisteredException(String username) {
        super("User " + username + " already registered.");
    }

}

package com.ivarrace.gringotts.application.exception;

public class InvalidParameterException extends RuntimeException {

    public InvalidParameterException(String parameterName) {
        super("Invalid parameter: " + parameterName);
    }

}

package com.ivarrace.gringotts.domain.exception;

public class InvalidParameterException extends RuntimeException {

    public InvalidParameterException(String parameterName, String value) {
        super(String.format("Invalid parameter: [%s] (with value [%s])", parameterName, value));
    }

}

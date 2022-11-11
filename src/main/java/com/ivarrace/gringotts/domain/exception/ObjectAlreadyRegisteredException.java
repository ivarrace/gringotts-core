package com.ivarrace.gringotts.domain.exception;

public class ObjectAlreadyRegisteredException extends RuntimeException {

    public ObjectAlreadyRegisteredException(String id) {
        super(String.format("Object [%s] already exists", id));
    }

}

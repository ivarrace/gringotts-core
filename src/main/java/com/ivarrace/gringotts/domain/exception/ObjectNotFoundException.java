package com.ivarrace.gringotts.domain.exception;

public class ObjectNotFoundException extends RuntimeException {

    public ObjectNotFoundException(String id) {
        super(String.format("Object [%s] not found", id));
    }

}

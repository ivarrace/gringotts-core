package com.ivarrace.gringotts.application.exception;

public class ObjectNotFoundException extends RuntimeException {

    public ObjectNotFoundException(String id) {
        super("Object " + id + " not found.");
    }

}

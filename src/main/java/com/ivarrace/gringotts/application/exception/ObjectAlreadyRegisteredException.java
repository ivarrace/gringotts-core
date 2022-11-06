package com.ivarrace.gringotts.application.exception;

public class ObjectAlreadyRegisteredException extends RuntimeException {

    public ObjectAlreadyRegisteredException(String id) {
        super("Object " + id + " already registered.");
    }

}

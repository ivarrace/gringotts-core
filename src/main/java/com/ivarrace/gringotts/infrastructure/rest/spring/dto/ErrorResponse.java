package com.ivarrace.gringotts.infrastructure.rest.spring.dto;

import java.time.LocalDateTime;

public class ErrorResponse {

    private String message;
    private LocalDateTime date;

    public ErrorResponse(String message){
        this.message = message;
        this.date = LocalDateTime.now();
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}

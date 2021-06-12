package com.cardgames.cardGameAPI.models.responses;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.http.HttpStatus;

public class UnauthorisedResponse {
    private String message;
    private LocalDateTime timeStamp;
    private HttpStatus status;
    private int error;

    @JsonCreator
    public UnauthorisedResponse() {
        this.message = "An unauthorised call to this endpoint has been made. Please include proper authorisation.";
        this.timeStamp = LocalDateTime.now();
        this.status = HttpStatus.FORBIDDEN;
        this.error = HttpStatus.FORBIDDEN.value();
    }
    
}
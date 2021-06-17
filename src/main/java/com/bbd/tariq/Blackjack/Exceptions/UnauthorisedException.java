package com.bbd.tariq.Blackjack.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorisedException extends RuntimeException{
    public UnauthorisedException(String message) {
        super(message);
    }
}

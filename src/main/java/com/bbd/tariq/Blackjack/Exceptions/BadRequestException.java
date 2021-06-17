package com.bbd.tariq.Blackjack.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BlackjackBadRequestException extends RuntimeException{
    public BlackjackBadRequestException(String message) {
        super(message);
    }
}

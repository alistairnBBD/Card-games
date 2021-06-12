package com.cardgames.cardGameAPI.controllers;

import com.cardgames.cardGameAPI.models.JSONError;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@RestController
public class CardGameErrorController implements ErrorController {

    //Message for page not existing
    private final String message404 = "This page does not exist";
    //Message for a failed authorization
    private final String message403 = "Authorization failed";
    //All other errors (for now)
    private final String message500 = "Unknown error";

    //Mapping for errors
    @RequestMapping("/error")
    public ResponseEntity<JSONError> handleError(HttpServletRequest request) {
        //Get the error code
        int statusCode = (int) request.getAttribute("javax.servlet.error.status_code");
        JSONError body;
        //Depending on the code create the correct error body and return an error response
        switch (statusCode) {
            case 404:
            body = new JSONError(message404, LocalDateTime.now(), HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
            case 403:
            body = new JSONError(message403, LocalDateTime.now(), HttpStatus.FORBIDDEN);
            return new ResponseEntity<>(body,HttpStatus.FORBIDDEN);
            default:
            body = new JSONError(message500,LocalDateTime.now(),HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(body,HttpStatus.NOT_FOUND);
        }
    }
}

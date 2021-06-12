package com.cardgames.cardGameAPI.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.http.HttpStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonDeserialize()
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class JSONError {

    private String message;
    private LocalDateTime timeStamp;
    private HttpStatus status;
    private int error;

    //Create the error in JSON format
    @JsonCreator
    public JSONError(@JsonProperty("Message") String message,
                     @JsonProperty("Timestamp") LocalDateTime timeStamp,
                     @JsonProperty("Status") HttpStatus status
    ) {
        this.message = message;
        this.timeStamp = timeStamp;
        this.status = status;
        this.error = status.value();
    }
}

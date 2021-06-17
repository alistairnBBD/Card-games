package com.cardgames.cardGameAPI.models.responses;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DeckCreationResponse {
    public String status;
    private String deckID;

    @JsonCreator
    public DeckCreationResponse(@JsonProperty("success") String status, @JsonProperty("deck_id") String deckID) {
        this.status = status;
        this.deckID = deckID;
    }

    public String getDeckID() {
        System.out.println(deckID);
        return deckID;
    }
}
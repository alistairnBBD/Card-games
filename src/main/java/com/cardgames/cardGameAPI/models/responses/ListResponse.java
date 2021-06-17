package com.cardgames.cardGameAPI.models.responses;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ListResponse {
    public String status;
    public String deckID;
    public Piles piles;
    public pileName pilename;

    @JsonCreator
    public ListResponse(@JsonProperty("success") String status, @JsonProperty("deck_id") String deck_id,
    @JsonProperty("piles") Piles piles, @JsonProperty("Anonymous") pileName pilename) {
        this.status = status;
        this.deckID = deck_id;
        this.piles = piles;
        this.pilename = pilename;
    }

}

class Piles {
    public pileName pilename;
}

class pileName {
    public Cards cards[];
    public String remaining;
}




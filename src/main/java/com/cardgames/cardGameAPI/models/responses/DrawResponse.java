package com.cardgames.cardGameAPI.models.responses;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DrawResponse {
    public String status;
    private Cards[] cards;

    

    @JsonCreator
    public DrawResponse(@JsonProperty("success") String status, @JsonProperty("cards") Cards[] cards) {
        this.status = status;
        this.cards = cards;
    }

    public String getCards() {
        String allCards = "";
        for(int i = 0; i < cards.length; i++)
        {
            allCards += cards[i].code + ",";
        }
        System.out.println("All cards:" + allCards);
        return allCards;
    }


}

class Cards {
    public String code;
}



package com.bbd.tariq.Blackjack.Models.CardsApiModels;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BaseCardsResponseModel {

    @JsonProperty("success")
    public boolean success;

    @JsonProperty("deck_id")
    public String deckId;

    @JsonProperty("remaining")
    public int remainingCards;
}

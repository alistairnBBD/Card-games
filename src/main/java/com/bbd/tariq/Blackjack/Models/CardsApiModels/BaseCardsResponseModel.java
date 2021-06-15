package com.bbd.tariq.Blackjack.Models.CardsApiModels;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BaseCardsResponseModel {

    @JsonProperty("success")
    public boolean success;

    @JsonProperty("deck_id")
    public String deckId;

    @JsonProperty("remaining")
    public int remainingCards;
}

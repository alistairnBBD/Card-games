package com.bbd.tariq.Blackjack.Models.CardsApiModels;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CardModel {

    @JsonProperty("image")
    public String imageUrl;

    @JsonProperty("value")
    public String value;

    @JsonProperty("suit")
    public String suit;

    @JsonProperty("code")
    public String code;


}

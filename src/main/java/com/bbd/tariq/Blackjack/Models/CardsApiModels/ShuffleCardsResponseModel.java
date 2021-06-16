package com.bbd.tariq.Blackjack.Models.CardsApiModels;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ShuffleCardsResponseModel extends BaseCardsResponseModel{

    @JsonProperty("shuffled")
    public boolean shuffled;
}

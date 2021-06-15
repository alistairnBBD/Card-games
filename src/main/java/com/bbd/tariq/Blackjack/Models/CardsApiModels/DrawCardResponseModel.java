package com.bbd.tariq.Blackjack.Models.CardsApiModels;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class DrawCardResponseModel extends BaseCardsResponseModel{

    @JsonProperty("cards")
    public ArrayList<CardModel> cards;
}

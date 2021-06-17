package com.bbd.tariq.Blackjack.Models.CardsApiModels;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class DrawCardResponseModel extends BaseCardsResponseModel{

    @JsonProperty("cards")
    public ArrayList<CardModel> cards;

    public String getCards() {

        String s = "";
        if(cards != null)
        for(int i = 0; i < cards.size(); i++){
            s += cards.get(i).code + ",";
        }
        return s;
    }
}

package com.bbd.tariq.Blackjack.Models.CardsApiModels.Piles;

import com.bbd.tariq.Blackjack.Models.CardsApiModels.CardModel;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class DrawCardFromPileResponseModel extends PilesBaseResponseModel{

    @JsonProperty("cards")
    public ArrayList<CardModel> cards;

    @Override
    public String getCards() {
        // TODO Auto-generated method stub
        return null;
    }

}

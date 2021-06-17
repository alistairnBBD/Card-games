package com.bbd.tariq.Blackjack.Models.CardsApiModels.Piles;

import com.bbd.tariq.Blackjack.Models.CardsApiModels.CardModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter @Setter
public class PileInfo {

    @JsonProperty("remaining")
    public int remainingCards;

    @JsonProperty("cards")
    public ArrayList<CardModel> cards;

    public String getCards() {
        String s = "";
        if(cards != null)
        for(int i = 0; i < cards.size(); i++)
            s += cards.get(i).code + ",";
        return s;
    }
}

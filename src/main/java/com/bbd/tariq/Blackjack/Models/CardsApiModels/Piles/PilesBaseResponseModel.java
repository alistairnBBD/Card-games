package com.bbd.tariq.Blackjack.Models.CardsApiModels.Piles;

import com.bbd.tariq.Blackjack.Models.CardsApiModels.CardModel;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class PilesBase {
    public PileInfo pileInfo;


    class PileInfo {
        @JsonProperty("remaining")
        public String remainingCards;

        @JsonProperty("cards")
        public ArrayList<CardModel> cards;
}

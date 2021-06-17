package com.bbd.tariq.Blackjack.Models.RummyGame;

import java.util.ArrayList;

import com.bbd.tariq.Blackjack.Models.CardsApiModels.CardModel;
import com.bbd.tariq.Blackjack.Models.CardsApiModels.Piles.PileInfo;
import com.bbd.tariq.Blackjack.Models.CardsApiModels.Piles.PilesBaseResponseModel;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RummyPilesResponseModel extends PilesBaseResponseModel {

    @JsonProperty("piles")
    public RummyPilesModel playerOnePile;

    public String getCards() {
        return playerOnePile.getCards();
    }

}

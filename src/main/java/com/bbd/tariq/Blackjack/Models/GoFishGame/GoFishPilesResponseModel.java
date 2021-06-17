package com.bbd.tariq.Blackjack.Models.GoFishGame;

import com.bbd.tariq.Blackjack.Models.CardsApiModels.Piles.PilesBaseResponseModel;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GoFishPilesResponseModel extends PilesBaseResponseModel {

    @JsonProperty("piles")
    public GoFishPilesModel playerOnePile;
}

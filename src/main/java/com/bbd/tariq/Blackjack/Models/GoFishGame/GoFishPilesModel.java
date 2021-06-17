package com.bbd.tariq.Blackjack.Models.GoFishGame;

import com.bbd.tariq.Blackjack.Models.CardsApiModels.Piles.PileInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.bbd.tariq.Blackjack.Common.Constants;

public class GoFishPilesModel {

    @JsonProperty(Constants.GoFishPileNames.PLAYER_1_PILE_NAME)
    public PileInfo playerOne;
}

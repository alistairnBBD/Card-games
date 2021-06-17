package com.bbd.tariq.Blackjack.Models.CardsApiModels.Piles.GoFish;

import com.bbd.tariq.Blackjack.Models.CardsApiModels.Piles.PileInfo;
import com.bbd.tariq.Blackjack.Models.CardsApiModels.Piles.PilesBaseResponseModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.bbd.tariq.Blackjack.Common.Constants;
import org.apache.tomcat.util.bcel.Const;

public class GoFishPilesModel {

    @JsonProperty(Constants.GoFishPileNames.PLAYER_1_PILE_NAME)
    public PileInfo playerOne;
}

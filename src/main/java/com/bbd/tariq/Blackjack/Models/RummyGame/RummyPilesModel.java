package com.bbd.tariq.Blackjack.Models.RummyGame;
import com.bbd.tariq.Blackjack.Models.CardsApiModels.Piles.PileInfo;
import com.bbd.tariq.Blackjack.Models.CardsApiModels.Piles.PilesBaseResponseModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.bbd.tariq.Blackjack.Common.Constants;
import org.apache.tomcat.util.bcel.Const;

public class RummyPilesModel {

    @JsonProperty("Player0")
    public PileInfo playerOne;

    @JsonProperty("Player1")
    public PileInfo playerTwo;

    @JsonProperty("Player2")
    public PileInfo playerThree;

    public String getCards() {
        return playerOne.getCards();
    }
}

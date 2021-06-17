package com.bbd.tariq.Blackjack.Models.CardsApiModels.Piles.Solitaire;

import com.bbd.tariq.Blackjack.Common.Constants;
import com.bbd.tariq.Blackjack.Models.CardsApiModels.Piles.PileInfo;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SolitairePilesModel {

    @JsonProperty(Constants.SolitairePileNames.DIAMONDS_PILE_NAME)
    public PileInfo diamondsPile;

    @JsonProperty(Constants.SolitairePileNames.SPADES_PILE_NAME)
    public PileInfo spadesPile;

    @JsonProperty(Constants.SolitairePileNames.HEARTS_PILE_NAME)
    public PileInfo heartsPile;

    @JsonProperty(Constants.SolitairePileNames.CLUBS_PILE_NAME)
    public PileInfo clubsPile;
}

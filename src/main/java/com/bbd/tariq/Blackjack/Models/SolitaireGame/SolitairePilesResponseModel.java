package com.bbd.tariq.Blackjack.Models.SolitaireGame;

import com.bbd.tariq.Blackjack.Models.CardsApiModels.Piles.PilesBaseResponseModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SolitairePilesResponseModel extends PilesBaseResponseModel {


    @JsonProperty("piles")
    public SolitairePilesModel solitairePiles;



}



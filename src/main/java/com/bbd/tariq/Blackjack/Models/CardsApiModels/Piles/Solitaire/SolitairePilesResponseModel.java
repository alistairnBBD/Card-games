package com.bbd.tariq.Blackjack.Models.CardsApiModels.Piles.Solitaire;

import com.bbd.tariq.Blackjack.Models.CardsApiModels.Piles.PilesBaseResponseModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SolitairePilesResponseModel extends PilesBaseResponseModel {


    @JsonProperty("piles")
    public SolitairePilesModel solitairePiles;

    @Override
    public String getCards() {
        // TODO Auto-generated method stub
        return null;
    }



}



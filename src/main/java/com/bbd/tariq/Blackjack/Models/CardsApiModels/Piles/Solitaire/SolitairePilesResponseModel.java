package com.bbd.tariq.Blackjack.Models.CardsApiModels.Piles;

import com.bbd.tariq.Blackjack.Common.Constants;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter @Setter
public class SolitairePilesResponseModel extends PilesBaseResponseModel {


    @JsonProperty("piles")
    public Piles piles;



}



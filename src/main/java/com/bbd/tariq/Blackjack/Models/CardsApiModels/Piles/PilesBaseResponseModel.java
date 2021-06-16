package com.bbd.tariq.Blackjack.Models.CardsApiModels.Piles;

import com.bbd.tariq.Blackjack.Models.CardsApiModels.BaseCardsResponseModel;
import com.bbd.tariq.Blackjack.Models.CardsApiModels.Piles.GoFish.GoFishPilesResponseModel;
import com.bbd.tariq.Blackjack.Models.CardsApiModels.Piles.Solitaire.SolitairePilesResponseModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter

public abstract class PilesBaseResponseModel extends BaseCardsResponseModel {

        public String type;
}

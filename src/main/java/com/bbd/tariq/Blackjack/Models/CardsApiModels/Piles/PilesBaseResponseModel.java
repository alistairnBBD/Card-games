package com.bbd.tariq.Blackjack.Models.CardsApiModels.Piles;

import com.bbd.tariq.Blackjack.Models.CardsApiModels.BaseCardsResponseModel;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter

public abstract class PilesBaseResponseModel extends BaseCardsResponseModel {

        public String type;
}

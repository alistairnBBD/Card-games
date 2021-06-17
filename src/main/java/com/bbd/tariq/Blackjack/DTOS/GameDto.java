package com.bbd.tariq.Blackjack.DTOS;

import com.bbd.tariq.Blackjack.Models.CardsApiModels.Piles.PilesBaseResponseModel;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GameDto {

    public String gameId;

    public PilesBaseResponseModel piles;
}

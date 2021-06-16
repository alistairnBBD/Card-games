package com.bbd.tariq.Blackjack.Interfaces;

import com.bbd.tariq.Blackjack.Models.CardsApiModels.Piles.PilesBaseResponseModel;

public interface IPileFactory {

    PilesBaseResponseModel getPile(String pileType);
}

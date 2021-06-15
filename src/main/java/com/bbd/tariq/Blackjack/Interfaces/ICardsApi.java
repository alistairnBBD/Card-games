package com.bbd.tariq.Blackjack.Interfaces;

import com.bbd.tariq.Blackjack.Models.CardsApiModels.*;

public interface ICardsApi {

    public ShuffleCardsResponseModel shuffleCards(int deckCount);
    public DrawCardResponseModel drawCards(String deckId,int cardCount);

}

package com.bbd.tariq.Blackjack.Interfaces;

import com.bbd.tariq.Blackjack.Models.CardsApiModels.*;
import com.bbd.tariq.Blackjack.Models.CardsApiModels.Piles.PilesBaseResponseModel;

import java.util.ArrayList;
import java.util.PriorityQueue;

public interface ICardsApi {

    public ShuffleCardsResponseModel getNewDeck(int deckCount);
    public ShuffleCardsResponseModel shuffleCards(String deckId);
    public DrawCardResponseModel drawCards(String deckId,int cardCount);
    public DrawCardResponseModel discardCards(String deckId, String playerId, String cards);
    public DrawCardResponseModel drawDiscard(String deckId);
    public ShuffleCardsResponseModel getPartialDeck(PriorityQueue<String> cards);
    public PilesBaseResponseModel addCardsToPile(String deckId, String pileType ,String pileName, String cards);
    public PilesBaseResponseModel shufflePile(String deckId, String pileType, String PileName);
    public PilesBaseResponseModel listCardsInPile(String deckId, String pileType, String pileName);



}

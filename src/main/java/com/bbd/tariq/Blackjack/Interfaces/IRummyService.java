package com.bbd.tariq.Blackjack.Interfaces;

import com.bbd.tariq.Blackjack.DTOS.PostNewGameDto;
import com.bbd.tariq.Blackjack.Models.GameModel;
import com.bbd.tariq.Blackjack.Models.CardsApiModels.Piles.PilesBaseResponseModel;
import com.bbd.tariq.Blackjack.Models.RummyGame.RummyGameModel;

public interface IRummyService {

    RummyGameModel newGame(PostNewGameDto newGameDto);
    RummyGameModel Pickup(String gameId, int playerId, Boolean fromDeck);
    RummyGameModel Discard( String gameId, int playerId,  String cards);
    RummyGameModel MakeAddSet( String gameId, int playerId, String setId,  String cards,  Boolean make);
    PilesBaseResponseModel getPiles( String deckId, String cardId);
    PilesBaseResponseModel getCardsInPile(String gameId, String pileId);
}

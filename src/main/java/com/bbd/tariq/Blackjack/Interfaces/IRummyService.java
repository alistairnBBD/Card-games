package com.bbd.tariq.Blackjack.Interfaces;

import com.bbd.tariq.Blackjack.DTOS.PostNewGameDto;
import com.bbd.tariq.Blackjack.Models.GameModel;
import com.bbd.tariq.Blackjack.Models.CardsApiModels.Piles.PilesBaseResponseModel;

public interface IRummyService {

    GameModel newGame(PostNewGameDto newGameDto);
    PilesBaseResponseModel Pickup(String gameId, String playerId, Boolean fromDeck);
    PilesBaseResponseModel Discard( String gameId, String playerId,  String cards);
    PilesBaseResponseModel MakeAddSet( String gameId, String playerId, String setId,  String cards,  Boolean make);
    PilesBaseResponseModel getPiles( String deckId, String cardId);
    PilesBaseResponseModel getCardsInPile(String gameId, String pileId);
}

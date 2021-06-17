package com.bbd.tariq.Blackjack.Interfaces;

import com.bbd.tariq.Blackjack.DTOS.PostNewGameDto;
import com.bbd.tariq.Blackjack.Models.BlackjackGame.BlackjackGameModel;

public interface IBlackjackService {

    BlackjackGameModel newGame(PostNewGameDto newGameDto);
    BlackjackGameModel hit(String gameId, int playerId);
    BlackjackGameModel stand(String gameId, int playerId);
}

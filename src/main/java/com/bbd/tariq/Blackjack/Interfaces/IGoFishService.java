package com.bbd.tariq.Blackjack.Interfaces;

import com.bbd.tariq.Blackjack.DTOS.PostNewGameDto;
import com.bbd.tariq.Blackjack.Models.GoFishGame.GoFishGameModel;

public interface IGoFishService {

    GoFishGameModel newGame(PostNewGameDto newGameDto);
    GoFishGameModel ask(String gameId, int playerId, int targetId, String card);
}

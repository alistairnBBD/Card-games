package com.bbd.tariq.Blackjack.Interfaces;

import com.bbd.tariq.Blackjack.Models.HiloGame.HiloGameModel;

public interface IHiloService {

    HiloGameModel newGame(String playerName);
    HiloGameModel guess(String gameId, String guess);
}

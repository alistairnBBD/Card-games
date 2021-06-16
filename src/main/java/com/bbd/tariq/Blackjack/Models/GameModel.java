package com.bbd.tariq.Blackjack.Models;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class GameModel {

    public long id;

    public String gameId;

    public String deckId;

    public int score;



    public GameModel() {
        gameId = UUID.randomUUID().toString();
    }
}

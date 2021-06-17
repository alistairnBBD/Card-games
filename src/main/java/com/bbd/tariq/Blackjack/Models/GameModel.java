package com.bbd.tariq.Blackjack.Models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

@Getter
@Setter
public class GameModel {

    public long id;

    public String gameId;

    public String deckId;

    public int score;

    public String gameState;

    public GameModel() {
        gameId = UUID.randomUUID().toString();

    }
}

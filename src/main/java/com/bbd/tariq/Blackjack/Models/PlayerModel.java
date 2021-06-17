package com.bbd.tariq.Blackjack.Models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class PlayerModel {

    public PlayerModel() {

    }

    public PlayerModel(int playerId, int score, String name) {
        this.playerId = playerId;
        this.score = score;
        this.name = name;
    }
    
    @JsonProperty("playerId")
    public int playerId;

    public int score;

    public String name;
}

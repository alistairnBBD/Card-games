package com.bbd.tariq.Blackjack.DTOS;

import com.bbd.tariq.Blackjack.Models.PlayerModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter @Setter
public class PostNewGameDto {

    @JsonProperty("deckCount")
    public int deckCount;
    @JsonProperty("players")
    public ArrayList<PlayerModel> players;

}

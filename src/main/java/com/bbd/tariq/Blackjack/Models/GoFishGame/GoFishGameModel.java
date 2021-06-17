package com.bbd.tariq.Blackjack.Models.GoFishGame;

import com.bbd.tariq.Blackjack.Models.GameModel;
import com.bbd.tariq.Blackjack.Models.GoFishGame.GoFishPlayer;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter @Setter
public class GoFishGameModel extends GameModel {

    public int turn  = 0;
    public ArrayList<GoFishPlayer> players;

    public GoFishGameModel(){
        players = new ArrayList<>();
    }
}

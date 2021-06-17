package com.bbd.tariq.Blackjack.Models.RummyGame;

import com.bbd.tariq.Blackjack.Models.GameModel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter @Setter
public class RummyGameModel extends GameModel {

    public int currentTurn = 0;
    public boolean pickedUp = false;
    public int playerCount = 3;

    public RummyGameModel(){
    }


}

package com.bbd.tariq.Blackjack.Models.BlackjackGame;

import com.bbd.tariq.Blackjack.Models.GameModel;
import com.bbd.tariq.Blackjack.Models.PlayerModel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter @Setter
public class BlackjackGameModel extends GameModel {

    public int currentTurn = 0;
    public int totalTurnsPerRound;
    public ArrayList<BlackjackPlayer> players;

    public BlackjackGameModel(){
        players = new ArrayList<>();
    }

    public BlackjackPlayer dealer;

}

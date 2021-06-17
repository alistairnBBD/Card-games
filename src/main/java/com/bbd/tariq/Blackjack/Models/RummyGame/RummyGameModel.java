package com.bbd.tariq.Blackjack.Models.RummyGame;

import com.bbd.tariq.Blackjack.Models.GameModel;
import com.bbd.tariq.Blackjack.Models.PlayerModel;
import com.bbd.tariq.Blackjack.Models.CardsApiModels.CardModel;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter @Setter
public class RummyGameModel extends GameModel {

    public int currentTurn = 0;
    public boolean pickedUp = false;
    public int playerCount = 3;
    public ArrayList<String> sets;
    public CardModel discardCard;

    public ArrayList<RummyPlayer> players;
    public String message;

    public RummyGameModel(){
        sets = new ArrayList<String>();
        players = new ArrayList<RummyPlayer>();
        discardCard = new CardModel();
        message = "Game in progrss";
    }


}

package com.bbd.tariq.Blackjack.Models.BlackjackGame;

import com.bbd.tariq.Blackjack.Models.CardsApiModels.CardModel;
import com.bbd.tariq.Blackjack.Models.PlayerModel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter @Setter
public class BlackjackPlayer extends PlayerModel {

    public ArrayList<CardModel> cards;
    public boolean bust;
    public boolean blackjack;
    public int score;
    public String action;

    public BlackjackPlayer() {
        cards = new ArrayList<>();
        bust = false;
        score = 0;
    }


}

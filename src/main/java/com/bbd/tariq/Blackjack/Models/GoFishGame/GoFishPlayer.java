package com.bbd.tariq.Blackjack.Models.GoFishGame;

import com.bbd.tariq.Blackjack.Models.CardsApiModels.CardModel;
import com.bbd.tariq.Blackjack.Models.PlayerModel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class GoFishPlayer extends PlayerModel {

    public @Getter ArrayList<CardModel> cards;
    public @Getter @Setter String action;

    public GoFishPlayer() {
        cards = new ArrayList<>();
        score = 0;
    }
}

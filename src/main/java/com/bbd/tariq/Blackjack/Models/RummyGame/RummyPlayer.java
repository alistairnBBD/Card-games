package com.bbd.tariq.Blackjack.Models.RummyGame;

import com.bbd.tariq.Blackjack.Models.CardsApiModels.CardModel;
import com.bbd.tariq.Blackjack.Models.PlayerModel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter @Setter
public class RummyPlayer extends PlayerModel {

    public ArrayList<CardModel> cards;

    public RummyPlayer() {
        cards = new ArrayList<>();
    }


}

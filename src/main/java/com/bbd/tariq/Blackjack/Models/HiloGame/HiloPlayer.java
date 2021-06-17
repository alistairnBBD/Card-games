package com.bbd.tariq.Blackjack.Models.HiloGame;

import com.bbd.tariq.Blackjack.Models.CardsApiModels.CardModel;
import com.bbd.tariq.Blackjack.Models.PlayerModel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter @Setter
public class HiloPlayer extends PlayerModel {

    public ArrayList<CardModel> cards;
    public String name;

    public HiloPlayer() {
        cards = new ArrayList<>();
    }
}

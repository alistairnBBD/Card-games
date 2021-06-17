package com.bbd.tariq.Blackjack.Models.HiloGame;

import com.bbd.tariq.Blackjack.Models.GameModel;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class HiloGameModel extends GameModel {

    public HiloPlayer player;

    public HiloGameModel(){
        player = new HiloPlayer();
        score = 0;
    }
}

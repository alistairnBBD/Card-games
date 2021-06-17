package com.cardgames.cardGameAPI.models;

import lombok.Data;

@Data
public class GoFishPlayer {
    private String _name;
    private String _hand;
    private String _sets;


    public GoFishPlayer(String name, String hand) {
        _name = name;
        _hand = hand;
    }

    public String getName() {
        return _name;
    }

    public String getHand() {
        return _hand;
    }

    public String getSets() {
        return _sets;
    }
    
}

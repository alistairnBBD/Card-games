package com.cardgames.cardGameAPI.models;

import lombok.Data;

@Data
public class RummyPlayer {
    private String _name;
    private String _hand;


    public RummyPlayer(String name, String hand) {
        _name = name;
        _hand = hand;
    }

    public String getName() {
        return _name;
    }

    public String getHand() {
        return _hand;
    }

}
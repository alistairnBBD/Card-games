package com.cardgames.cardGameAPI.models;

import lombok.Data;

@Data
public class PlayerHand {
    private String _name;
    private String _deckID;
    private String[] hand;

    public PlayerHand(String name, String deckID, String[] hand) {
        _name = name;
        _deckID = deckID;
        this.hand = hand;
    }

    public String getName() {
        return _name;
    }

    public String getDeckId() {
        return _deckID;
    }

    public String[] getHand() {
        return hand;
    }
}
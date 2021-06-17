package com.cardgames.cardGameAPI.models;

import lombok.Data;

@Data
public class Player {
    private String _name;
    private String _deckID;
    private String message;

    public Player(String name, String deckID, String message) {
        _name = name;
        _deckID = deckID;
        this.message = message;
    }

    public String getName() {
        return _name;
    }

    public String getDeckId() {
        return _deckID;
    }

    public String getMessage() {
        return message;
    }
}
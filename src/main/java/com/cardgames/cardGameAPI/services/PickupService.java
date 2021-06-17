package com.cardgames.cardGameAPI.services;

import com.cardgames.cardGameAPI.models.responses.DrawResponse;

import org.springframework.web.client.RestTemplate;

public class PickupService {
    private static RestTemplate _rest = new RestTemplate();

    public static DrawResponse pickupDeck(String deckID, String playerName) {
        DrawResponse response = _rest.getForObject("https://deckofcardsapi.com/api/deck/" + deckID + "/draw/?count=1", DrawResponse.class);
        return response;
    }

    public static DrawResponse pickupDiscard(String deckID, String playerName) {
        DrawResponse response = _rest.getForObject("https://deckofcardsapi.com/api/deck/" + deckID + "pile/discard/draw/bottom/", DrawResponse.class);
        return response;
    }
}
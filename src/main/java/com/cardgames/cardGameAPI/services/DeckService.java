package com.cardgames.cardGameAPI.services;

import com.cardgames.cardGameAPI.models.responses.DeckCreationResponse;

import org.springframework.web.client.RestTemplate;

public class DeckService {
    private static RestTemplate _rest = new RestTemplate();

    public static String createNewDeck() {
        DeckCreationResponse response = _rest.getForObject("https://deckofcardsapi.com/api/deck/new", DeckCreationResponse.class);
        return response.getDeckID();
    }
}
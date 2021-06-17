package com.cardgames.cardGameAPI.services;

import com.cardgames.cardGameAPI.models.responses.DrawResponse;

import org.springframework.web.client.RestTemplate;

public class DiscardService {
    private static RestTemplate _rest = new RestTemplate();

    public static DrawResponse discardFirstCard(String deckID) {
        DrawResponse response = _rest.getForObject("https://deckofcardsapi.com/api/deck/"+deckID+"/draw/?cards=1", DrawResponse.class);
        _rest.getForObject("https://deckofcardsapi.com/api/deck/"+deckID+"/pile/discard/add/?cards=" + response.getCards(), DrawResponse.class);
        return response;
    }

    public static DrawResponse discardCard(String deckID, String playerName, String card) {
        DrawResponse response = _rest.getForObject("https://deckofcardsapi.com/api/deck/"+deckID+"/pile/"+playerName+"/draw/?cards=" + card, DrawResponse.class);
        _rest.getForObject("https://deckofcardsapi.com/api/deck/"+deckID+"/pile/"+playerName+"/add/?cards=" + response.getCards() + card, DrawResponse.class);
        return response;
    }

}
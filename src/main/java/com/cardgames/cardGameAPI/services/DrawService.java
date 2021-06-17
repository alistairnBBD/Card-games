package com.cardgames.cardGameAPI.services;

import com.cardgames.cardGameAPI.models.responses.DrawResponse;

import org.springframework.web.client.RestTemplate;

public class DrawService {
    private static RestTemplate _rest = new RestTemplate();

    public static String drawHand(String deckID, String playerName) {
        DrawResponse response = _rest.getForObject("https://deckofcardsapi.com/api/deck/" + deckID + "/draw/?count=7", DrawResponse.class);
        _rest.getForObject("https://deckofcardsapi.com/api/deck/"+deckID+"/pile/"+playerName+"/add/?cards="+response.getCards(), DrawResponse.class);
        return response.getCards();
    }
}
package com.cardgames.cardGameAPI.services;

import com.cardgames.cardGameAPI.models.responses.DrawResponse;

import org.springframework.web.client.RestTemplate;

public class PlaceSetService {
    private static RestTemplate _rest = new RestTemplate();


    public static String addCreateSet(String deckID, String playerName, String cards, String pile) {
        DrawResponse response = _rest.getForObject("https://deckofcardsapi.com/api/deck/"+deckID+"/pile/"+playerName+"/draw/?cards=" + cards, DrawResponse.class);
        _rest.getForObject("https://deckofcardsapi.com/api/deck/"+deckID+"/pile/"+pile+"/add/?cards=" + cards, DrawResponse.class);
        if (response == null) {
            return "";
        }
        return response.getCards();
    }
}
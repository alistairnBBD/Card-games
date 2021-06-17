package com.cardgames.cardGameAPI.services;

import com.cardgames.cardGameAPI.models.responses.ListResponse;

import org.springframework.web.client.RestTemplate;

public class ViewService {
    private static RestTemplate _rest = new RestTemplate();

    public static ListResponse viewCards(String deckID, String pile) {
        ListResponse response = _rest.getForObject("https://deckofcardsapi.com/api/deck/"+deckID+"/pile/"+pile+"/list/", ListResponse.class);
        return response;
    }
}
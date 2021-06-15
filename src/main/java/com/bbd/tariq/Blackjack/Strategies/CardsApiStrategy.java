package com.bbd.tariq.Blackjack.Strategies;

import com.bbd.tariq.Blackjack.Interfaces.ICardsApi;
import com.bbd.tariq.Blackjack.Models.CardsApiModels.DrawCardResponseModel;
import com.bbd.tariq.Blackjack.Models.CardsApiModels.ShuffleCardsResponseModel;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CardsApiStrategy implements ICardsApi {
    @Override
    public ShuffleCardsResponseModel shuffleCards(int deckCount) {
        HttpClient client = HttpClient.newHttpClient();

        var request = HttpRequest.newBuilder(
                URI.create(String.format("https://deckofcardsapi.com/api/deck/new/shuffle/?deck_count=%s",deckCount)))
                .header("accept","application/json")
                .build();

        try {
            var response =  client.send(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper mapper = new ObjectMapper();
            ShuffleCardsResponseModel shuffleCardsResponse = mapper.readValue(response.body(),ShuffleCardsResponseModel.class);
            return shuffleCardsResponse;
        }
        catch(Exception ex)
        {
            //Need to implement some sort of logging interface
            System.out.println(ex);
            return null;
        }
    }

    @Override
    public DrawCardResponseModel drawCards(String deckId, int cardCount) {
        HttpClient client = HttpClient.newHttpClient();

        var request = HttpRequest.newBuilder(
                URI.create(String.format("https://deckofcardsapi.com/api/deck/%s/draw/?count=%s",deckId,cardCount)))
                .header("accept","application/json")
                .build();

        try {
            var response =  client.send(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            DrawCardResponseModel drawCardResponseModel = mapper.readValue(response.body(),DrawCardResponseModel.class);
            return drawCardResponseModel;
        }
        catch(Exception ex)
        {
            //Need to implement some sort of logging interface
            System.out.println(ex);
            return null;
        }
    }

}

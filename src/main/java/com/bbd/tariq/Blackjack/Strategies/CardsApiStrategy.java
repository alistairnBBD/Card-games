package com.bbd.tariq.Blackjack.Strategies;

import com.bbd.tariq.Blackjack.Interfaces.ICardsApi;
import com.bbd.tariq.Blackjack.Interfaces.IPileFactory;
import com.bbd.tariq.Blackjack.Models.CardsApiModels.DrawCardResponseModel;
import com.bbd.tariq.Blackjack.Models.CardsApiModels.Piles.DrawCardFromPileResponseModel;
import com.bbd.tariq.Blackjack.Models.CardsApiModels.Piles.PilesBaseResponseModel;
import com.bbd.tariq.Blackjack.Models.CardsApiModels.ShuffleCardsResponseModel;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.PriorityQueue;

public class CardsApiStrategy implements ICardsApi {

    private final HttpClient _httpClient;
    private final ObjectMapper _mapper;
    private final IPileFactory _pileFactory;

    public CardsApiStrategy(IPileFactory pileFactory) {
        _httpClient = HttpClient.newHttpClient();
        _mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        _pileFactory = pileFactory;
    }

    @Override
    public ShuffleCardsResponseModel getNewDeck(int deckCount) {

        var request = HttpRequest.newBuilder(
                URI.create(String.format("https://deckofcardsapi.com/api/deck/new/shuffle/?deck_count=%s",deckCount)))
                .header("accept","application/json")
                .build();

        try {
            var response =  _httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            ShuffleCardsResponseModel shuffleCardsResponse = _mapper.readValue(response.body(),ShuffleCardsResponseModel.class);
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
    public ShuffleCardsResponseModel shuffleCards(String deckID){
        var request = HttpRequest.newBuilder(
                URI.create(String.format("https://deckofcardsapi.com/api/deck/%s/shuffle/",deckID)))
                .header("accept","application/json")
                .build();

        try {
            var response =  _httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            ShuffleCardsResponseModel shuffleCardsResponse = _mapper.readValue(response.body(),ShuffleCardsResponseModel.class);
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

        var request = HttpRequest.newBuilder(
                URI.create(String.format("https://deckofcardsapi.com/api/deck/%s/draw/?count=%s",deckId,cardCount)))
                .header("accept","application/json")
                .build();

        try {
            var response =  _httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            DrawCardResponseModel drawCardResponseModel = _mapper.readValue(response.body(),DrawCardResponseModel.class);
            return drawCardResponseModel;
        }
        catch(Exception ex)
        {
            //Need to implement some sort of logging interface
            System.out.println(ex);
            return null;
        }
    }

    //TODO, Do we even use this?
    @Override
    public ShuffleCardsResponseModel getPartialDeck(PriorityQueue<String> cards) {
        return null;
    }

    @Override
    public PilesBaseResponseModel addCardsToPile(String deckId, String pileType, String pileName, String cards) {

          var request = HttpRequest.newBuilder(
                URI.create(String.format("https://deckofcardsapi.com/api/deck/%s/pile/%s/add/?cards=%s",deckId,pileName,cards)))
                .header("accept","application/json")
                .build();

        try {
            var response =  _httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            PilesBaseResponseModel pileModel = _pileFactory.getPile(pileType);
            PilesBaseResponseModel addToPileResponseModel = _mapper.readValue(response.body(),pileModel.getClass());
            addToPileResponseModel.type = pileModel.type;
            return addToPileResponseModel;
        }
        catch(Exception ex)
        {
            //Need to implement some sort of logging interface
            System.out.println(ex);
            return null;
        }
    }

    //TODO
    @Override
    public PilesBaseResponseModel shufflePile(String deckId, String pileType, String PileName) {
        var request = HttpRequest.newBuilder(
                URI.create(String.format("https://deckofcardsapi.com/api/deck/%s/pile/%s/shuffle/",deckId, PileName)))
                .header("accept","application/json")
                .build();

        try {
            var response =  _httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            var pileModel = _pileFactory.getPile(pileType);
            var addToPileResponseModel = _mapper.readValue(response.body(),pileModel.getClass());
            return addToPileResponseModel;
        }
        catch(Exception ex)
        {
            //Need to implement some sort of logging interface
            System.out.println(ex);
            return null;
        }
    }

    @Override
    public PilesBaseResponseModel listCardsInPile(String deckId, String pileType, String pileName) {

        var request = HttpRequest.newBuilder(
                URI.create(String.format("https://deckofcardsapi.com/api/deck/%s/pile/%s/list/",deckId,pileName)))
                .header("accept","application/json")
                .build();

        try {
            var response =  _httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            var pileModel = _pileFactory.getPile(pileType);
            var addToPileResponseModel = _mapper.readValue(response.body(),pileModel.getClass());
            return addToPileResponseModel;
        }
        catch(Exception ex)
        {
            //Need to implement some sort of logging interface
            System.out.println(ex);
            return null;
        }
    }

    @Override
    public DrawCardFromPileResponseModel drawFromPile(String deckId, String pileType, String pileName, String cards) {
        var request = HttpRequest.newBuilder(
                URI.create(String.format("https://deckofcardsapi.com/api/deck/%s/pile/%s/draw/?cards=%s",deckId,pileName,cards)))
                .header("accept","application/json")
                .build();

        try {
            var response =  _httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            var addToPileResponseModel = _mapper.readValue(response.body(),DrawCardFromPileResponseModel.class);
            return addToPileResponseModel;
        }
        catch(Exception ex)
        {
            //Need to implement some sort of logging interface
            System.out.println(ex);
            return null;
        }
    }

    @Override
    public DrawCardFromPileResponseModel drawFromPile(String deckId, String pileType, String pileName, int cardCount) {
        var request = HttpRequest.newBuilder(
                URI.create(String.format("https://deckofcardsapi.com/api/deck/%s/pile/%s/draw/?count=%s",deckId,pileName,cardCount)))
                .header("accept","application/json")
                .build();

        try {
            var response =  _httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            var addToPileResponseModel = _mapper.readValue(response.body(),DrawCardFromPileResponseModel.class);
            return addToPileResponseModel;
        }
        catch(Exception ex)
        {
            //Need to implement some sort of logging interface
            System.out.println(ex);
            return null;
        }
    }

}

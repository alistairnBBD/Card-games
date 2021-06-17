package com.cardgames.cardGameAPI.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.val;

import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Arrays;

import com.cardgames.cardGameAPI.models.GoFishPlayer;
import com.cardgames.cardGameAPI.models.responses.DrawResponse;
import com.cardgames.cardGameAPI.models.responses.ListResponse;
import com.cardgames.cardGameAPI.services.DeckService;
import com.cardgames.cardGameAPI.services.DiscardService;
import com.cardgames.cardGameAPI.services.DrawService;
import com.cardgames.cardGameAPI.services.PickupService;
import com.cardgames.cardGameAPI.services.PlaceSetService;
import com.cardgames.cardGameAPI.services.ViewService;

@RestController
public class GoFishController {

    String deckID;

    @CrossOrigin(origins = "*")
    @GetMapping("/gofish")
    public ResponseEntity<GoFishPlayer[]> gofish(@RequestParam(value = "Players", defaultValue = "Anonymous") String[] players,
                                                @RequestHeader(name = "Authorization", required = false) String auth) {
        //Check if the correct authorization is there
        if (auth != null && auth.equals("12345")) {
            // Check db if player exists else new player, new deck
            if (players.length < 2 || players.length > 5) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            GoFishPlayer[] playerArr = new GoFishPlayer[players.length];
            deckID = DeckService.createNewDeck();
            for(int i = 0; i < players.length; i++)
            {
                playerArr[i] = new GoFishPlayer(players[i], DrawService.drawHand(deckID, players[i]));
                System.out.println(playerArr[i].getHand());
            }
            return new ResponseEntity<>(playerArr, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/gofish/request")
    public ResponseEntity<DrawResponse> pickupCard(@RequestParam(value = "Player", defaultValue = "Anonymous") String player,
                        @RequestParam(value = "Deck", defaultValue = "true") Boolean deck,
                                                @RequestHeader(name = "Authorization", required = false) String auth) {
        if (auth != null && auth.equals("12345")) {
            DrawResponse res;
            if(deck)
                res = PickupService.pickupDeck(deckID, player);
            else
                res = PickupService.pickupDiscard(deckID, player);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/gofish/view")
    public ResponseEntity<ListResponse> viewCards(@RequestParam(value = "Pile", defaultValue = "Anonymous") String pile,
                                                @RequestHeader(name = "Authorization", required = false) String auth) {
        if (auth != null && auth.equals("12345")) {
            ListResponse res = ViewService.viewCards(deckID, pile);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }

    @CrossOrigin(origins = "*")
    @GetMapping("/gofish/addToSet")
    public ResponseEntity<String> addToSet(@RequestParam(value = "Player") String player,
                                            @RequestParam(value = "Cards") String cards,
                                            @RequestParam(value = "Pile") String pile,
                                                @RequestHeader(name = "Authorization", required = false) String auth) {
        if (auth != null && auth.equals("12345")) {
            String res = PlaceSetService.addCreateSet(deckID, player, cards, pile);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }
}
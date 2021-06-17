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

import com.cardgames.cardGameAPI.models.RummyPlayer;
import com.cardgames.cardGameAPI.models.responses.DrawResponse;
import com.cardgames.cardGameAPI.models.responses.ListResponse;
import com.cardgames.cardGameAPI.services.DeckService;
import com.cardgames.cardGameAPI.services.DiscardService;
import com.cardgames.cardGameAPI.services.DrawService;
import com.cardgames.cardGameAPI.services.PickupService;
import com.cardgames.cardGameAPI.services.PlaceSetService;
import com.cardgames.cardGameAPI.services.ViewService;

@RestController
public class RummyController {

    String deckID;

    @CrossOrigin(origins = "*")
    @GetMapping("/rummy")
    public ResponseEntity<RummyPlayer[]> rummy(@RequestParam(value = "Players", defaultValue = "Anonymous") String[] players,
                                                @RequestHeader(name = "Authorization", required = false) String auth) {
        //Check if the correct authorization is there
        if (auth != null && auth.equals("12345")) {
            // Check db if player exists else new player, new deck 
            RummyPlayer[] player = new RummyPlayer[players.length];
            deckID = DeckService.createNewDeck();
            for(int i = 0; i < players.length; i++)
            {
                player[i] = new RummyPlayer(players[i], DrawService.drawHand(deckID, players[i]));
                System.out.println(player[i].getHand());
            }
            DiscardService.discardFirstCard(deckID);
            return new ResponseEntity<>(player, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/rummy/pickup")
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
    @GetMapping("/rummy/discard")
    public ResponseEntity<DrawResponse> discardCard(@RequestParam(value = "Player", defaultValue = "Anonymous") String player,
                        @RequestParam(value = "Card") String card,
                                                @RequestHeader(name = "Authorization", required = false) String auth) {
        if (auth != null && auth.equals("12345")) {
            System.out.println(deckID);
            System.out.println(player);
            System.out.println(card);
            DrawResponse res = DiscardService.discardCard(deckID, player, card);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/rummy/view")
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
    @GetMapping("/rummy/createSet")
    public ResponseEntity<String> createSet(@RequestParam(value = "Player") String player,
                                            @RequestParam(value = "Cards") String[] cards,
                                                @RequestHeader(name = "Authorization", required = false) String auth) {
        if (auth != null && auth.equals("12345")) {
            char[] suit = new char[cards.length];
            int[] val = new int[cards.length];
            boolean valueMatch = true;
            boolean suitMatch = true;
            boolean valueFollows = true;
            boolean canSplit = false;

            if(val.length <= 2)
                return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);

            for(int i = 0; i < cards.length; i++) {
                char cval = cards[i].charAt(0);
                switch (cval) {
                    case 'J': val[i] = 11;
                        break;
                    case 'Q': val[i] = 12;
                        break;
                    case 'K': val[i] = 13;
                        break;  
                    case 'A': val[i] = 1;
                        break; 
                    default: val[i] = Character.getNumericValue(cval);
                        break;    
                }
                suit[i] = cards[i].charAt(1);
            }

            Arrays.sort(val);
            if(val[0] == 1 && val[val.length-1] == 13)
                canSplit = true;

            for(int i = 1; i < val.length; i++)
            {
                if(val[0] != val[i]) 
                    valueMatch=false;
                if(suit[0] != suit[i]) 
                    suitMatch=false;
                if(val[i] - 1 != val[i - 1])
                {
                    if(canSplit)
                        canSplit = false;
                    else
                        valueFollows=false;
                }
            }
            
            if(valueMatch || (suitMatch && valueFollows)){
                String res = PlaceSetService.addCreateSet(deckID, player, String.join(",",cards), "myPile");
                return new ResponseEntity<>(res, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.FAILED_DEPENDENCY);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }

    @CrossOrigin(origins = "*")
    @GetMapping("/rummy/addToSet")
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
package com.cardgames.cardGameAPI.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.cardgames.cardGameAPI.models.Player;
import com.cardgames.cardGameAPI.models.responses.UnauthorisedResponse;
import com.cardgames.cardGameAPI.services.DeckService;

@RestController
public class SolitaireController {
    private final UnauthorisedResponse unauthorisedResponse = new UnauthorisedResponse();
    
    @CrossOrigin(origins = "*")
    @GetMapping("/solitaire")
    public ResponseEntity<Player> solitaire(@RequestParam(value = "Name", defaultValue = "Anonymous") String name,
                                                @RequestHeader(name = "Authorization", required = false) String auth) {
        //Check if the correct authorization is there
        if (auth != null && auth.equals("12345")) {
            // Check db if player exists else new player, new deck
            Player player = new Player(name, DeckService.createNewDeck(), "Success");
            return new ResponseEntity<>(player, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
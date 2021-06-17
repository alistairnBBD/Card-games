package com.bbd.tariq.Blackjack.Controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import com.bbd.tariq.Blackjack.Common.Constants;
import com.bbd.tariq.Blackjack.DTOS.GameDto;
import com.bbd.tariq.Blackjack.DTOS.PostNewGameDto;
import com.bbd.tariq.Blackjack.Interfaces.ICardsApi;
import com.bbd.tariq.Blackjack.Interfaces.IRepoFactory;
import com.bbd.tariq.Blackjack.Models.CardsApiModels.DrawCardResponseModel;
import com.bbd.tariq.Blackjack.Models.CardsApiModels.Piles.PilesBaseResponseModel;
import com.bbd.tariq.Blackjack.Models.CardsApiModels.Piles.Rummy.RummyPilesResponseModel;
import com.fasterxml.jackson.databind.util.JSONWrappedObject;
import com.bbd.tariq.Blackjack.Models.GameModel;

import org.apache.tomcat.util.json.JSONParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rummy")
@Component
public class RummyController extends BaseController {


    public RummyController(ICardsApi cardsApi, ModelMapper mapper, IRepoFactory repoFactory) {
        super(cardsApi, mapper, repoFactory,"blackjack");

    }

    @PostMapping(value = "/newGame", consumes = "application/json")
    public GameModel newGame(@RequestBody PostNewGameDto postNewGameDto) {
        /*
        * Logic:
        *   1. FE hits this endpoint and we create a new game
        *   2. Generate a game id
        *   3. Generate x deck of cards via ICardsApi
        *   4. Store game id and deck id in db
        *   5. Send back Json payload
        * */

        var deckOfCards = _cardsApi.getNewDeck(postNewGameDto.deckCount);
        GameModel rummyModel = new GameModel();
        rummyModel.deckId = deckOfCards.deckId;
        _repo.Insert(rummyModel);
        GameDto dto = _mapper.map(rummyModel, GameDto.class);

        for(int i = 0; i < postNewGameDto.users.size(); i++)
        {
            String cards = _cardsApi.drawCards(rummyModel.deckId, 7).getCards();
            System.out.println(i + " - " + cards);
            _cardsApi.addCardsToPile(rummyModel.deckId, "Rummy", "Player" + i, cards);
        }
        _cardsApi.addCardsToPile(rummyModel.deckId, "Rummy", "discard", _cardsApi.drawCards(rummyModel.deckId, 1).getCards());

        return rummyModel;
    }

    @PostMapping(value = "/pickup")
    public PilesBaseResponseModel Pickup(@RequestParam String gameId, String playerId, @RequestParam Boolean fromDeck) {
        if(gameId.isEmpty()|| playerId.isEmpty()) {
            //return some error code
            return null;
        }

        var game = _repo.Get(gameId);
        String res;
        if(fromDeck)
        {
            res = _cardsApi.drawCards(game.deckId, 1).getCards(); //PickupService.pickupDeck(deckID, player);
            System.out.println("deck " + res);
        }
        else
        {
            res = _cardsApi.drawDiscard(game.deckId).getCards();
            System.out.println("not deck " + res);
        }
        
        var drawnCards = _cardsApi.addCardsToPile(game.deckId, "Rummy", playerId, res);
        return drawnCards;
    }

    @PostMapping(value = "/discard")
    public PilesBaseResponseModel Discard(@RequestParam String gameId, String playerId, @RequestParam String cards) {
        if(gameId.isEmpty()|| playerId.isEmpty()) {
            //return some error code
            return null;
        }

        var game = _repo.Get(gameId);
        String res;

        res = _cardsApi.discardCards(game.deckId, playerId, cards).getCards(); //PickupService.pickupDeck(deckID, player);
        
        var drawnCards = _cardsApi.addCardsToPile(game.deckId, "Rummy", "discard", res);
        return drawnCards;
    }

    @PostMapping(value = "/makeAddSet")
    public PilesBaseResponseModel MakeAddSet(@RequestParam String gameId, String playerId, String setId, @RequestParam String cards, @RequestParam Boolean make) {
        if(gameId.isEmpty()|| setId.isEmpty()) {
            //return some error code
            return null;
        }

        System.out.println("cards " + cards);

        var game = _repo.Get(gameId);
        ArrayList<String> tempCards = new ArrayList<String>(Arrays.asList(cards.split(",")));
        ArrayList<Character> suit = new ArrayList<Character>();
        ArrayList<Integer> val = new ArrayList<Integer>();
        boolean valueMatch = true;
        boolean suitMatch = true;
        boolean valueFollows = true;
        boolean canSplit = false;

        System.out.println("Size " + tempCards.size());

        if(make && tempCards.size() <= 2) {
            //return some error code
            System.out.println("Check -2");
            return null;
        }
        else if (!make){
            System.out.println("Check -1");
            PilesBaseResponseModel newCards = _cardsApi.listCardsInPile(game.deckId, "Rummy", setId);
            Collections.addAll(tempCards, newCards.getCards().split("\\s*,\\s*"));
        }

        System.out.println("Check 0");
            
        for(int i = 0; i < tempCards.size(); i++) {
            char cval = tempCards.get(i).charAt(0);
            switch (cval) {
                case '0': val.add(10);
                    break;
                case 'J': val.add(11);
                    break;
                case 'Q': val.add(12);
                    break;
                case 'K': val.add(13);
                    break;  
                case 'A': val.add(14);
                    break; 
                default: val.add(Character.getNumericValue(cval));
                    break;    
            }
            suit.add(tempCards.get(i).charAt(1));
        }

        System.out.println("Check 1");

        Collections.sort(val);
        if(val.get(0) == 1 && val.get(val.size()-1) == 13)
            canSplit = true;

            System.out.println("Check 2");

        for(int i = 1; i < val.size(); i++)
        {
            if(val.get(0) != val.get(i)) 
                valueMatch=false;
            if(suit.get(0) != suit.get(i)) 
                suitMatch=false;
            if(val.get(i) - 1 != val.get(i - 1))
            {
                if(canSplit)
                    canSplit = false;
                else
                    valueFollows=false;
            }
        }

        System.out.println("Check 3");
        
        if(valueMatch || (suitMatch && valueFollows)){
            System.out.println("Check 4");
            _cardsApi.discardCards(game.deckId, playerId, cards);
            var drawnCards = _cardsApi.addCardsToPile(game.deckId, "Rummy", setId, cards);
            return drawnCards;
        }
        else
        {
            return null;
        }
    }

    //Solitaire
    @GetMapping(value="/test")
    public PilesBaseResponseModel getPiles(@RequestParam String deckId, String cardId) {

        var x = _cardsApi.addCardsToPile(deckId, Constants.PileTypes.GO_FISH,Constants.GoFishPileNames.PLAYER_1_PILE_NAME,cardId);

        return x;
    }

    //Solitaire
    @GetMapping(value="/listpiles")
    public PilesBaseResponseModel getCardsInPile(String gameId, String pileId)
    {
        var game = _repo.Get(gameId);
        var x = _cardsApi.listCardsInPile(game.deckId,"Rummy",pileId);

        return x;
    }

}

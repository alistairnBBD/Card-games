package com.bbd.tariq.Blackjack.Strategies;

import com.bbd.tariq.Blackjack.Common.Constants;
import com.bbd.tariq.Blackjack.DTOS.PostNewGameDto;
import com.bbd.tariq.Blackjack.Exceptions.BadRequestException;
import com.bbd.tariq.Blackjack.Interfaces.ICardsApi;
import com.bbd.tariq.Blackjack.Interfaces.IRepoFactory;
import com.bbd.tariq.Blackjack.Interfaces.IRummyService;
import com.bbd.tariq.Blackjack.Models.CardsApiModels.CardModel;
import com.bbd.tariq.Blackjack.Models.CardsApiModels.DrawCardResponseModel;
import com.bbd.tariq.Blackjack.Models.CardsApiModels.Piles.PilesBaseResponseModel;
import com.bbd.tariq.Blackjack.Models.RummyGame.RummyGameModel;
import com.bbd.tariq.Blackjack.Models.RummyGame.RummyPlayer;
import com.bbd.tariq.Blackjack.Models.GameModel;
import com.bbd.tariq.Blackjack.Models.PlayerModel;
import com.bbd.tariq.Blackjack.Repos.Repo;
import com.bbd.tariq.Blackjack.Repos.RummyRepo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class RummyServiceStrategy implements IRummyService {

    private final ICardsApi _cardsApi;
    private final Repo _rummyRepo;

    public RummyServiceStrategy(ICardsApi cardsApi, IRepoFactory repoFactory) {

        _cardsApi = cardsApi;
        _rummyRepo = repoFactory.getRepo(Constants.Blackjack.REPO_NAME);
    }

    public RummyGameModel newGame(PostNewGameDto newGameDto) {
        /*
        * Logic:
        *   1. FE hits this endpoint and we create a new game
        *   2. Generate a game id
        *   3. Generate x deck of cards via ICardsApi
        *   4. Store game id and deck id in db
        *   5. Send back Json payload
        * */
        var deckOfCards = _cardsApi.getNewDeck(newGameDto.deckCount);
        RummyGameModel rummyModel = new RummyGameModel();
        rummyModel.deckId = deckOfCards.deckId;
        rummyModel.currentTurn = 0;
        rummyModel.pickedUp = false;
        rummyModel.playerCount = newGameDto.players.size();
        rummyModel.gameState = Constants.GameStates.RUNNING;
        _rummyRepo.Insert(rummyModel);
        for(int i = 0; i < newGameDto.players.size(); i++)
        {
            DrawCardResponseModel res = _cardsApi.drawCards(rummyModel.deckId, 7);
            RummyPlayer player = new RummyPlayer();
            player.playerId = newGameDto.players.get(i).playerId;
            player.name = newGameDto.players.get(i).name;
            player.score = 0;
            player.cards = res.cards;
            rummyModel.players.add(player);
            _cardsApi.addCardsToPile(rummyModel.deckId, "Rummy", "Player" + i, res.getCards());
        }
        CardModel discard = _cardsApi.drawCards(rummyModel.deckId, 1).cards.get(0);
        _cardsApi.addCardsToPile(rummyModel.deckId, "Rummy", "discard", discard.code);
        rummyModel.discardCard = discard;
        return rummyModel;
    }

    public RummyGameModel Pickup(String gameId, int playerId, Boolean fromDeck) {
        checkTurn(gameId, playerId, true);
        RummyGameModel rgm = (RummyGameModel) _rummyRepo.Get(gameId);
        if(gameId.isEmpty()|| playerId < 0) {
            //return some error code
            throw new BadRequestException(String.format("No Players"));
        }

        var game = _rummyRepo.Get(gameId);
        DrawCardResponseModel res;
        if(fromDeck)
        {
            res = _cardsApi.drawCards(game.deckId, 1);
            if(res.cards == null)
            {
                rgm.gameState = Constants.GameStates.COMPLETE;
                rgm.message = "Deck is empty, game is a draw";
                return rgm;
            }
        }
        else
        {
            res = _cardsApi.drawDiscard(game.deckId);
        }
        
        var drawnCards = _cardsApi.addCardsToPile(game.deckId, "Rummy", "Player" + playerId, res.getCards());
        rgm.players.get(playerId).cards.add(res.cards.get(0));
        
        rgm.pickedUp = true;
        return rgm;
    }

    public RummyGameModel Discard( String gameId, int playerId,  String cards) {
        checkTurn(gameId, playerId, false);
        RummyGameModel rgm = (RummyGameModel) _rummyRepo.Get(gameId);
        if(gameId.isEmpty()|| playerId<0) {
            //return some error code
            throw new BadRequestException(String.format("No Players"));
        }

        var game = _rummyRepo.Get(gameId);
        DrawCardResponseModel res;

        res = _cardsApi.discardCards(game.deckId, "Player" + playerId, cards); //PickupService.pickupDeck(deckID, player);
        if(res.cards == null)
            throw new BadRequestException(String.format("The card %d is not in Player %s's hand",cards,playerId));
        var drawnCards = _cardsApi.addCardsToPile(game.deckId, "Rummy", "discard", res.getCards());
        rgm.discardCard = res.cards.get(0);
        rgm.players.get(playerId).cards.removeIf(n -> n.code.compareTo(cards) == 0);
        rgm.pickedUp = false;
        if(rgm.currentTurn == rgm.playerCount - 1)
            rgm.currentTurn = 0;
        else
            rgm.currentTurn = rgm.currentTurn + 1;
            

        if(checkComplete(gameId,"Player"+playerId))
            drawnCards.win = true;
        return rgm;
    }

    public RummyGameModel MakeAddSet( String gameId, int playerId, String setId,  String cards,  Boolean make) {
        checkTurn(gameId, playerId, false);
        RummyGameModel rgm = (RummyGameModel) _rummyRepo.Get(gameId);
        if(gameId.isEmpty()|| setId.isEmpty()) {
            //return some error code
            throw new BadRequestException(String.format("No Players"));
        }

        var game = _rummyRepo.Get(gameId);
        ArrayList<String> tempCards = new ArrayList<String>(Arrays.asList(cards.split(",")));
        ArrayList<Character> suit = new ArrayList<Character>();
        ArrayList<Integer> val = new ArrayList<Integer>();
        boolean valueMatch = true;
        boolean suitMatch = true;
        boolean valueFollows = true;
        boolean canSplit = false;


        if(make && tempCards.size() <= 2) {
            //return some error code
            throw new BadRequestException(String.format("Not a valid set. Set should consist of atleast 3 cards."));
        }
        else if (!make){
            PilesBaseResponseModel newCards = _cardsApi.listCardsInPile(game.deckId, "Rummy", setId);
            Collections.addAll(tempCards, newCards.getCards().split("\\s*,\\s*"));
        }

            
        for(int i = 0; i < tempCards.size(); i++) {
            if(tempCards.get(i).length() > 0)
            {
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
        }


        Collections.sort(val);
        if(val.get(0) == 1 && val.get(val.size()-1) == 13)
            canSplit = true;


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

        
        if(valueMatch || (suitMatch && valueFollows)){
            _cardsApi.discardCards(game.deckId, "Player"+playerId, cards);
            _cardsApi.addCardsToPile(game.deckId, "Rummy", setId, cards);
            rgm.sets.remove(setId);
            rgm.sets.add(cards);
            for(int i = 0; i < rgm.players.size(); i++)
            {
                rgm.players.get(i).cards.removeIf(n -> cards.contains(n.code));
            }
            return rgm;
        }
        else
        {
            throw new BadRequestException(String.format("Not a valid set"));
        }
    }


    public PilesBaseResponseModel getPiles( String deckId, String cardId) {

        var x = _cardsApi.addCardsToPile(deckId, Constants.PileTypes.RUMMY,Constants.GoFishPileNames.PLAYER_1_PILE_NAME,cardId);

        return x;
    }

    public PilesBaseResponseModel getCardsInPile(String gameId, String pileId)
    {
        var game = _rummyRepo.Get(gameId);
        var x = _cardsApi.listCardsInPile(game.deckId,"Rummy",pileId);

        return x;
    }

    private void checkTurn(String gameId, int playerId, Boolean pickUp) {
        RummyGameModel rgm = (RummyGameModel) _rummyRepo.Get(gameId);

        if(playerId != rgm.currentTurn)
        {
            throw new BadRequestException(String.format("ERROR: Not %s's turn, Current Players turn: %s",playerId,"Player"+rgm.currentTurn));
        }
        if(pickUp && rgm.pickedUp==true)
        {
            throw new BadRequestException(String.format("ERROR: Player already picked up"));
        }
        if(!pickUp && rgm.pickedUp==false)
        {
            throw new BadRequestException(String.format("ERROR: Player must pick up before discarding or creating sets"));
        }

    }

    private boolean checkComplete(String gameId, String playerId) {
        RummyGameModel rgm = (RummyGameModel) _rummyRepo.Get(gameId);
        if(this.getCardsInPile(gameId, playerId).getCards().length() == 0)
        {
            rgm.message = playerId+" wins";
            rgm.gameState = Constants.GameStates.COMPLETE;
            return true;
        }
        rgm.gameState = Constants.GameStates.RUNNING;
        return false;
    }

}

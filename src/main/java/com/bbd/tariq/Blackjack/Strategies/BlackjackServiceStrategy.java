package com.bbd.tariq.Blackjack.Strategies;

import com.bbd.tariq.Blackjack.Common.Constants;
import com.bbd.tariq.Blackjack.DTOS.PostNewGameDto;
import com.bbd.tariq.Blackjack.Exceptions.BadRequestException;
import com.bbd.tariq.Blackjack.Interfaces.IBlackjackService;
import com.bbd.tariq.Blackjack.Interfaces.ICardsApi;
import com.bbd.tariq.Blackjack.Interfaces.IRepoFactory;
import com.bbd.tariq.Blackjack.Models.BlackjackGame.BlackjackGameModel;
import com.bbd.tariq.Blackjack.Models.BlackjackGame.BlackjackPlayer;
import com.bbd.tariq.Blackjack.Models.PlayerModel;
import com.bbd.tariq.Blackjack.Repos.Repo;

import java.util.HashMap;

public class BlackjackServiceStrategy implements IBlackjackService {

    private final ICardsApi _cardsApi;
    private final HashMap<String, PlayerModel> _dealers;
    private final Repo _blackjackRepo;

    public BlackjackServiceStrategy(ICardsApi cardsApi, IRepoFactory repoFactory) {

        _cardsApi = cardsApi;
        _dealers = new HashMap<>();
        _blackjackRepo = repoFactory.getRepo(Constants.Blackjack.REPO_NAME);
    }

    @Override
    public BlackjackGameModel newGame(PostNewGameDto newGameDto) {

        //Initial Setup of the game
        if (newGameDto.players.size() > 4) {
            System.out.println(String.format("ERROR: Players Requested: %s, Max Players Allowed: %s", newGameDto.players.size(), Constants.Blackjack.MAX_PLAYERS));
            return null;
        }

        var newDeck = _cardsApi.getNewDeck(newGameDto.deckCount);
        BlackjackGameModel blackjackGameModel = new BlackjackGameModel();

        for(var player : newGameDto.players)
        {
            BlackjackPlayer bjp = new BlackjackPlayer();
            bjp.playerId = player.playerId;
            blackjackGameModel.players.add(bjp);
        }
        blackjackGameModel.gameState = Constants.GameStates.RUNNING;
        blackjackGameModel.deckId = newDeck.deckId;
        blackjackGameModel.score = 0;
        blackjackGameModel.currentTurn = 1; //player 1 turn
        blackjackGameModel.totalTurnsPerRound = blackjackGameModel.players.size()+1; //+1 for the dealer;

        //Dealer setup
        blackjackGameModel.dealer = new BlackjackPlayer();
        blackjackGameModel.dealer.playerId = 999015235; //Arbitrary big int for dealer id
        blackjackGameModel.dealer.name = String.format("Dealer %s", _dealers.size());


        int numOfPlayers = blackjackGameModel.players.size();
        int totalCards = numOfPlayers + 1;
        int dealerCardIdx = numOfPlayers;

        //Deal Cards out to Players and Dealer
        for (int i = 0; i < 2; i++) {
            var drawnCards = _cardsApi.drawCards(blackjackGameModel.deckId, totalCards);
            String drawnCard = null;
            for (int j = 0; j < numOfPlayers; j++) {
                var currentPlayer = blackjackGameModel.players.get(j);
                currentPlayer.cards.add(drawnCards.cards.get(j));
                currentPlayer.score = CalculateScore(currentPlayer);
                currentPlayer.blackjack = isBlackjack(currentPlayer);
            }
            blackjackGameModel.dealer.cards.add(drawnCards.cards.get(dealerCardIdx));
            blackjackGameModel.dealer.score = CalculateScore(blackjackGameModel.dealer);
        }


        _dealers.put(blackjackGameModel.gameId,  blackjackGameModel.dealer);
        
        _blackjackRepo.Insert(blackjackGameModel);

       return blackjackGameModel;

    }

    @Override
    public BlackjackGameModel hit(String gameId, int playerId) {
        var blackjackGameModel = (BlackjackGameModel) _blackjackRepo.Get(gameId);

        if(blackjackGameModel.gameState.equals(Constants.GameStates.COMPLETE)) {
            throw new BadRequestException(String.format("Game %s is Complete",gameId));
        }

        var player = blackjackGameModel.players.stream().filter(p ->p.playerId == playerId).findFirst().orElse(null);

        preliminaryChecks(blackjackGameModel,player,playerId);

        var drawnCard = _cardsApi.drawCards(blackjackGameModel.deckId,1).cards.get(0);
        player.cards.add(drawnCard);
        player.score = CalculateScore(player);
        player.blackjack = isBlackjack(player);
        player.action = "hit";

        if(player.score>21)
        {
            player.bust = true;
            blackjackGameModel.currentTurn++;
        }
        else if(player.blackjack) {
            blackjackGameModel.currentTurn++;
        }


        //last player on the table has played, lets piggy back and do the dealers move as well
        if(blackjackGameModel.currentTurn == blackjackGameModel.players.size())
        {
            blackjackGameModel =  performDealerMoves(blackjackGameModel);
        }

        blackjackGameModel = determineOutcome(blackjackGameModel);
        _blackjackRepo.Update(blackjackGameModel);
        return blackjackGameModel;
    }

    @Override
    public BlackjackGameModel stand(String gameId, int playerId) {
        var blackjackGameModel = (BlackjackGameModel) _blackjackRepo.Get(gameId);

        if(blackjackGameModel.gameState.equals(Constants.GameStates.COMPLETE)) {
            throw new BadRequestException(String.format("Game %s is Complete",gameId));
        }

        var player = blackjackGameModel.players.stream().filter(p ->p.playerId == playerId).findFirst().orElse(null);

        preliminaryChecks(blackjackGameModel,player,playerId);

        player.action = Constants.Blackjack.BlackJackActions.STAND;
        blackjackGameModel.currentTurn++;

        //last player on the table has played, lets piggy back and do the dealers move as well
        if(blackjackGameModel.currentTurn == blackjackGameModel.players.size()+1)
        {
            blackjackGameModel =  performDealerMoves(blackjackGameModel);
        }
        blackjackGameModel = determineOutcome(blackjackGameModel);
        _blackjackRepo.Update(blackjackGameModel);
        return blackjackGameModel;
    }

    private int CalculateScore(BlackjackPlayer blackjackPlayer) {
        var cards = blackjackPlayer.cards;
        int score = 0;
        for(var card : blackjackPlayer.cards) {

            if(card.value.equals("KING") || card.value.equals("QUEEN") || card.value.equals("JACK"))
            {
               score+= Constants.Blackjack.BlackJackCardValues.JACK_QUEEN_KING;
               continue;
            }
            score+= Constants.Blackjack.CARD_VALUE_MAP.get(card.value);
        }
        return score;
    }

    private boolean isBlackjack(BlackjackPlayer blackjackPlayer) {
        if(blackjackPlayer.score==21)
            return true;

        return false;
    }

    private BlackjackGameModel performDealerMoves(BlackjackGameModel blackjackGameModel) {
       var dealer = blackjackGameModel.dealer;
        var drawnCard = _cardsApi.drawCards(blackjackGameModel.deckId,1).cards.get(0);
        if(blackjackGameModel.dealer.score >17) {
            blackjackGameModel.dealer.action = Constants.Blackjack.BlackJackActions.STAND; //Dealer has to stand
            return blackjackGameModel;
        }
        else if(blackjackGameModel.dealer.score <=16) {
            blackjackGameModel.dealer.action = Constants.Blackjack.BlackJackActions.HIT;
            blackjackGameModel.dealer.cards.add(drawnCard);
        }
        else if(drawnCard.value.equals(Constants.Blackjack.CARD_VALUE_MAP.get("ACE"))) {
            dealer.cards.add(drawnCard);
            int score = CalculateScore(blackjackGameModel.dealer);
            if(17 <= score && score <= 21) {
                blackjackGameModel.dealer.action = Constants.Blackjack.BlackJackActions.STAND;
            }

        }
        blackjackGameModel.currentTurn =1;
        return blackjackGameModel;
    }

    private void preliminaryChecks(BlackjackGameModel blackjackGameModel, BlackjackPlayer player, int requestedPlayerId) {

        if(player.playerId != blackjackGameModel.currentTurn) {
            System.out.println(String.format("ERROR: Not Player %s turn, Current Players turn: %s",requestedPlayerId,blackjackGameModel.currentTurn));
            throw new BadRequestException(String.format("ERROR: Not Player %s's turn, Current Players turn: %s",requestedPlayerId,blackjackGameModel.currentTurn));
        }
        if(player.blackjack) {
            System.out.println(String.format("ERROR: Player %s has gotten Blackjack this round already!", player.playerId));
            throw new BadRequestException(String.format("ERROR: Player %s has gotten Blackjack this round already!", player.playerId));

        }

    }
    //TODO
    private BlackjackGameModel determineOutcome(BlackjackGameModel blackjackGameModel) {

        //Check if there is an active player (bust = false)
        var activePlayer = blackjackGameModel.players.stream().filter(player -> !player.bust).findFirst().orElse(null);

        // dealer bust
        if(blackjackGameModel.dealer.bust) {
            blackjackGameModel.gameState = Constants.GameStates.COMPLETE;
        }

        else if(activePlayer!=null) {
            blackjackGameModel.gameState = Constants.GameStates.RUNNING;
        }

        return blackjackGameModel;
    }
}

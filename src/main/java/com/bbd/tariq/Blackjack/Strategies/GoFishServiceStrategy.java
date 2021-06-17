package com.bbd.tariq.Blackjack.Strategies;

import com.bbd.tariq.Blackjack.Common.Constants;
import com.bbd.tariq.Blackjack.DTOS.PostNewGameDto;
import com.bbd.tariq.Blackjack.Exceptions.BadRequestException;
import com.bbd.tariq.Blackjack.Exceptions.ContentNotFoundException;
import com.bbd.tariq.Blackjack.Interfaces.IGoFishService;
import com.bbd.tariq.Blackjack.Interfaces.ICardsApi;
import com.bbd.tariq.Blackjack.Interfaces.IRepoFactory;
import com.bbd.tariq.Blackjack.Models.GoFishGame.GoFishGameModel;
import com.bbd.tariq.Blackjack.Models.GoFishGame.GoFishPlayer;
import com.bbd.tariq.Blackjack.Models.PlayerModel;
import com.bbd.tariq.Blackjack.Repos.Repo;

import java.util.HashMap;
import java.util.ArrayList;

public class GoFishServiceStrategy implements IGoFishService {

    private final ICardsApi _cardsApi;
    private final Repo _gofishRepo;

    public GoFishServiceStrategy(ICardsApi cardsApi, IRepoFactory repoFactory) {

        _cardsApi = cardsApi;
        _gofishRepo = repoFactory.getRepo(Constants.GoFish.REPO_NAME);
    }

    @Override
    public GoFishGameModel newGame(PostNewGameDto newGameDto) {

        //Initial Setup of the game
        if (newGameDto.players.size() > Constants.GoFish.MAX_PLAYERS || newGameDto.players.size() < Constants.GoFish.MIN_PLAYERS) {
            throw new BadRequestException(String.format("ERROR: Players Requested: %s, Min Players Allowed: %s, Max Players Allowed: %s",
                newGameDto.players.size(), Constants.GoFish.MIN_PLAYERS, Constants.GoFish.MAX_PLAYERS));
        }

        var newDeck = _cardsApi.getNewDeck(newGameDto.deckCount);
        GoFishGameModel gofishGameModel = new GoFishGameModel();

        for(var player : newGameDto.players)
        {
            GoFishPlayer gfp = new GoFishPlayer();
            gfp.playerId = player.playerId;
            gofishGameModel.players.add(gfp);
        }

        gofishGameModel.deckId = newDeck.deckId;
        gofishGameModel.score = 0;
        gofishGameModel.turn = 1; //player 1 turn

        //Deal Cards out to Players
        for (int i = 0; i < 2; i++) {
            int draws = 7;
            var drawnCards = _cardsApi.drawCards(gofishGameModel.deckId, draws);
            for (int j = 0; j < draws; j++) {
                var currentPlayer = gofishGameModel.players.get(j);
                currentPlayer.cards.add(drawnCards.cards.get(j));
            }
        }

        _gofishRepo.Insert(gofishGameModel);

       return gofishGameModel;

    }

    @Override
    public GoFishGameModel ask(String gameId, int playerId, int targetId, char card) {
        var gofishGameModel = (GoFishGameModel) _gofishRepo.Get(gameId);
        var player = gofishGameModel.players.stream().filter(p ->p.playerId == playerId).findFirst().orElse(null);
        var target = gofishGameModel.players.stream().filter(p ->p.playerId == targetId).findFirst().orElse(null);

        preliminaryChecks(gofishGameModel,player,playerId);

        if (player.cards.isEmpty()) {
            int winner = determineOutcome(gofishGameModel.players);
            gofishGameModel.gameState = String.format("Player %s won", winner);
            return gofishGameModel;
        }
        var targetCards = target.cards.stream().filter(c -> c.code.charAt(0) == card).findAny().orElse(null);
        if (targetCards != null) {
            //take cards
            //check set
            return gofishGameModel;
        }
        var drawnCard = _cardsApi.drawCards(gofishGameModel.deckId,1).cards.get(0);
        player.cards.add(drawnCard);
        player.action = "ask";


        _gofishRepo.Update(gofishGameModel);
        return gofishGameModel;
    }
    
    private void preliminaryChecks(GoFishGameModel gofishGameModel, GoFishPlayer player, int requestedPlayerId) {

        if(player == null || player.playerId != gofishGameModel.turn) {
            throw new BadRequestException(String.format("ERROR: Not Player %s's turn, Current Players turn: %s",requestedPlayerId,gofishGameModel.turn));
        }

    }
    //TODO
    private int determineOutcome(ArrayList<GoFishPlayer> players) {
        int winner = 0;
        int highscore = 0;

        for (GoFishPlayer player : players) {
            if (player.score >= highscore) {
                highscore = player.score;
                winner = player.playerId;
            }
        }
        return winner;
    }
}

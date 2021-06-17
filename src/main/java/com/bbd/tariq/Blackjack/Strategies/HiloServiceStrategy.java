package com.bbd.tariq.Blackjack.Strategies;

import com.bbd.tariq.Blackjack.Common.Constants;
import com.bbd.tariq.Blackjack.Exceptions.ServerException;
import com.bbd.tariq.Blackjack.Interfaces.ICardsApi;
import com.bbd.tariq.Blackjack.Interfaces.IHiloService;
import com.bbd.tariq.Blackjack.Interfaces.IRepoFactory;
import com.bbd.tariq.Blackjack.Models.CardsApiModels.CardModel;
import com.bbd.tariq.Blackjack.Models.CardsApiModels.DrawCardResponseModel;
import com.bbd.tariq.Blackjack.Models.CardsApiModels.ShuffleCardsResponseModel;
import com.bbd.tariq.Blackjack.Models.HiloGame.HiloGameModel;
import com.bbd.tariq.Blackjack.Models.HiloGame.HiloPlayer;
import com.bbd.tariq.Blackjack.Repos.Repo;

public class HiloServiceStrategy implements IHiloService {

    private final ICardsApi _cardsApi;
    private final Repo _hiloRepo;

    public HiloServiceStrategy(ICardsApi cardsApi, IRepoFactory repoFactory) {

        _cardsApi = cardsApi;
        _hiloRepo = repoFactory.getRepo(Constants.Hilo.REPO_NAME);
    }

    @Override
    public HiloGameModel newGame(String playerName) {
        try {
            ShuffleCardsResponseModel newDeck = _cardsApi.getNewDeck(1);
            HiloGameModel hiloGameModel = new HiloGameModel();
            HiloPlayer player = new HiloPlayer();

            player.name = playerName;
            hiloGameModel.player = player;
            hiloGameModel.deckId = newDeck.deckId;

            DrawCardResponseModel drawnCard = _cardsApi.drawCards(hiloGameModel.deckId, 1);
            hiloGameModel.player.cards.add(drawnCard.cards.get(0));
            _hiloRepo.Insert(hiloGameModel);

            return hiloGameModel;
        } catch (Exception e) {
            throw new ServerException("The cards have bent. Please try again later.");
        }
    }

    @Override
    public HiloGameModel guess(String gameId, String guess) {
        try {
            HiloGameModel gameModel = (HiloGameModel) _hiloRepo.Get(gameId);

            CardModel lastCard = gameModel.player.cards.get(gameModel.player.cards.size() - 1);
            DrawCardResponseModel drawnCard = _cardsApi.drawCards(gameModel.deckId, 1);

            if (drawnCard.cards == null || drawnCard.cards.size() == 0) {
                _cardsApi.shuffleCards(gameModel.deckId);
                int playerStackSize = gameModel.player.cards.size();
                for (int i = 0; i < playerStackSize; i++) {
                    gameModel.player.cards.remove(0);
                }

                drawnCard = _cardsApi.drawCards(gameModel.deckId, 1);
                if (drawnCard.cards.get(0).code.equals(lastCard.code)) {
                    drawnCard = _cardsApi.drawCards(gameModel.deckId, 1);
                }
            }

            int newValue = Constants.Hilo.CARD_VALUE_MAP.get(drawnCard.cards.get(0).value);
            int oldValue = Constants.Hilo.CARD_VALUE_MAP.get(lastCard.value);
            String outcome =  newValue > oldValue ? Constants.Hilo.Guesses.HIGH :
                newValue == oldValue ? Constants.Hilo.Guesses.SAME : Constants.Hilo.Guesses.LOW;
                
            if (guess.equals(outcome)) {
                gameModel.score += newValue;
            } else {
                gameModel.score -= oldValue;
            }

            gameModel.player.cards.add(drawnCard.cards.get(0));
            _hiloRepo.Update(gameModel);

            return gameModel;
        } catch (Exception e) {
            throw new ServerException("The cards have bent. Please try again later.");
        }
    }
}

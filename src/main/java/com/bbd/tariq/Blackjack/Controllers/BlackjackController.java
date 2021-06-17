package com.bbd.tariq.Blackjack.Controllers;

import com.bbd.tariq.Blackjack.Common.Constants;
import com.bbd.tariq.Blackjack.DTOS.GameDto;
import com.bbd.tariq.Blackjack.DTOS.PostNewGameDto;
import com.bbd.tariq.Blackjack.Interfaces.IBlackjackService;
import com.bbd.tariq.Blackjack.Interfaces.ICardsApi;
import com.bbd.tariq.Blackjack.Interfaces.IRepoFactory;
import com.bbd.tariq.Blackjack.Models.BlackjackGame.BlackjackGameModel;
import com.bbd.tariq.Blackjack.Models.CardsApiModels.DrawCardResponseModel;
import com.bbd.tariq.Blackjack.Models.CardsApiModels.Piles.DrawCardFromPileResponseModel;
import com.bbd.tariq.Blackjack.Models.CardsApiModels.Piles.PilesBaseResponseModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/blackjack")
@Component
public class BlackjackController extends BaseController {

    private IBlackjackService _blackjackService;

    public BlackjackController(ICardsApi cardsApi, ModelMapper mapper, IRepoFactory repoFactory, IBlackjackService blackjackService) {
        super(cardsApi, mapper, repoFactory,"blackjack");

        _blackjackService = blackjackService;
    }

    @GetMapping(value = "/newgame", consumes = "application/json")
    public BlackjackGameModel newGame(@RequestBody PostNewGameDto postNewGameDto) {
        /*
        * Logic:
        *   1. FE hits this endpoint and we create a new game
        *   2. Generate a game id
        *   3. Generate x deck of cards via ICardsApi
        *   4. Store game id and deck id in db
        *   5. Send back Json payload
        * */
        return _blackjackService.newGame(postNewGameDto);
    }

    @PostMapping(value = "/hit")
    public BlackjackGameModel Hit(@RequestParam String gameId, int playerId) {

        return _blackjackService.hit(gameId,playerId);
    }

    @PostMapping(value = "/stand")
    public BlackjackGameModel Stand(@RequestParam String gameId, int playerId) {

        return _blackjackService.stand(gameId,playerId);
    }

    //Solitaire
    @GetMapping(value="/test")
    public PilesBaseResponseModel getPiles(@RequestParam String deckId, String cardId) {

        PilesBaseResponseModel x = _cardsApi.addCardsToPile(deckId, Constants.PileTypes.SOLITAIRE,Constants.SolitairePileNames.SPADES_PILE_NAME,cardId);
        return x;
    }

    //Solitaire
    @GetMapping(value="/listpiles")
    public PilesBaseResponseModel getCardsInPile(String deckId)
    {
     return _cardsApi.listCardsInPile(deckId,Constants.PileTypes.SOLITAIRE,Constants.SolitairePileNames.SPADES_PILE_NAME);

    }

    @GetMapping(value="/addToPile")
    public DrawCardFromPileResponseModel drawCardFromPile(String deckId) {
        return _cardsApi.drawFromPile(deckId,Constants.PileTypes.SOLITAIRE, Constants.SolitairePileNames.SPADES_PILE_NAME,1);
    }
}

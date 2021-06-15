package com.bbd.tariq.Blackjack.Controllers;

import com.bbd.tariq.Blackjack.DTOS.GameDto;
import com.bbd.tariq.Blackjack.DTOS.PostNewGameDto;
import com.bbd.tariq.Blackjack.Interfaces.ICardsApi;
import com.bbd.tariq.Blackjack.Interfaces.IRepoFactory;
import com.bbd.tariq.Blackjack.Models.CardsApiModels.DrawCardResponseModel;
import com.bbd.tariq.Blackjack.Models.GameModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;

@RestController
@RequestMapping("/blackjack")
@Component
public class BlackjackController extends BaseController {


    public BlackjackController(ICardsApi cardsApi, ModelMapper mapper, IRepoFactory repoFactory) {
        super(cardsApi, mapper, repoFactory,"blackjack");

    }

    @PostMapping(value = "/newGame", consumes = "application/json")
    public GameDto newGame(@RequestBody PostNewGameDto postNewGameDto) {
        /*
        * Logic:
        *   1. FE hits this endpoint and we create a new game
        *   2. Generate a game id
        *   3. Generate x deck of cards via ICardsApi
        *   4. Store game id and deck id in db
        *   5. Send back Json payload
        * */

        var deckOfCards = _cardsApi.shuffleCards(postNewGameDto.deckCount);
        GameModel blackJackModel = new GameModel();
        blackJackModel.deckId = deckOfCards.deckId;
        _repo.Insert(blackJackModel);
        GameDto dto = _mapper.map(blackJackModel, GameDto.class);
        return dto;
    }

    @PostMapping(value = "/hit")
    public DrawCardResponseModel Hit(@RequestParam String gameId, String playerId) {
        if(gameId.isEmpty()|| playerId.isEmpty()) {
            //return some error code
            return null;
        }

        var game = _repo.Get(gameId);
        var drawnCards = _cardsApi.drawCards(game.deckId,2);
        return drawnCards;
    }

}

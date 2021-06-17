package com.bbd.tariq.Blackjack.Controllers;

import com.bbd.tariq.Blackjack.Common.Constants;
import com.bbd.tariq.Blackjack.DTOS.GameDto;
import com.bbd.tariq.Blackjack.DTOS.PostNewGameDto;
import com.bbd.tariq.Blackjack.Interfaces.IGoFishService;
import com.bbd.tariq.Blackjack.Interfaces.ICardsApi;
import com.bbd.tariq.Blackjack.Interfaces.IRepoFactory;
import com.bbd.tariq.Blackjack.Models.GoFishGame.GoFishGameModel;
import com.bbd.tariq.Blackjack.Models.CardsApiModels.DrawCardResponseModel;
import com.bbd.tariq.Blackjack.Models.CardsApiModels.Piles.DrawCardFromPileResponseModel;
import com.bbd.tariq.Blackjack.Models.CardsApiModels.Piles.PilesBaseResponseModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/gofish")
@Component
public class GoFishController extends BaseController {

    private IGoFishService _gofishService;

    public GoFishController(ICardsApi cardsApi, ModelMapper mapper, IRepoFactory repoFactory, IGoFishService gofishService) {
        super(cardsApi, mapper, repoFactory,"blackjack");

        _gofishService = gofishService;
    }

    @GetMapping(value = "/newgame", consumes = "application/json")
    public GoFishGameModel newGame(@RequestBody PostNewGameDto postNewGameDto) {
        /*
        * Logic:
        *   1. FE hits this endpoint and we create a new game
        *   2. Generate a game id
        *   3. Generate x deck of cards via ICardsApi
        *   4. Store game id and deck id in db
        *   5. Send back Json payload
        * */
        return _gofishService.newGame(postNewGameDto);
    }

    @PostMapping(value = "/ask")
    public GoFishGameModel Ask(@RequestParam String gameId, int playerId, int targetId, String card) {

        return _gofishService.ask(gameId,playerId,targetId,card);
    }

}

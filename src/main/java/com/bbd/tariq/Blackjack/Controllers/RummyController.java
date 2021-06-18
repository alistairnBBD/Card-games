package com.bbd.tariq.Blackjack.Controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import com.bbd.tariq.Blackjack.Common.Constants;
import com.bbd.tariq.Blackjack.DTOS.GameDto;
import com.bbd.tariq.Blackjack.DTOS.PostNewGameDto;
import com.bbd.tariq.Blackjack.Exceptions.BadRequestException;
import com.bbd.tariq.Blackjack.Interfaces.ICardsApi;
import com.bbd.tariq.Blackjack.Interfaces.IRepoFactory;
import com.bbd.tariq.Blackjack.Interfaces.IRummyService;
import com.bbd.tariq.Blackjack.Models.CardsApiModels.DrawCardResponseModel;
import com.bbd.tariq.Blackjack.Models.CardsApiModels.Piles.PilesBaseResponseModel;
import com.bbd.tariq.Blackjack.Models.RummyGame.RummyGameModel;
import com.bbd.tariq.Blackjack.Models.RummyGame.RummyPilesResponseModel;
import com.bbd.tariq.Blackjack.Strategies.RummyServiceStrategy;
import com.fasterxml.jackson.databind.util.JSONWrappedObject;
import com.bbd.tariq.Blackjack.Models.GameModel;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.apache.tomcat.util.json.JSONParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rummy")
@ApiImplicitParams({
        @ApiImplicitParam(name = "token", required = true, value = "token",
                paramType = "header", dataType = "String")
})
@Component
public class RummyController extends BaseController {
    private IRummyService _rummyService;

    public RummyController(ICardsApi cardsApi, ModelMapper mapper, IRepoFactory repoFactory, IRummyService RummyService) {
        super(cardsApi, mapper, repoFactory,"rummy");
        _rummyService = RummyService;
    }

    @PostMapping(value = "/newGame", consumes = "application/json")
    public GameModel newGame(@RequestBody PostNewGameDto postNewGameDto) {
        return _rummyService.newGame(postNewGameDto);
    }

    @PostMapping(value = "/pickup")
    public RummyGameModel Pickup(@RequestParam String gameId, int playerId, @RequestParam Boolean fromDeck) {
        return _rummyService.Pickup(gameId, playerId, fromDeck);
    }

    @PostMapping(value = "/discard")
    public RummyGameModel Discard(@RequestParam String gameId, int playerId, @RequestParam String cards) {
       return _rummyService.Discard(gameId, playerId, cards);
    }

    @PostMapping(value = "/makeAddSet")
    public RummyGameModel MakeAddSet(@RequestParam String gameId, int playerId, @RequestParam(name = "setId", required = false) String setId, @RequestParam String cards, @RequestParam Boolean make) {
        if(setId == null && make)
        {
            RummyGameModel rgm = (RummyGameModel) _repo.Get(gameId);
            return MakeAddSet(gameId, playerId, rgm.sets.size()+"", cards, make);
        }
        if(setId == null && !make)
            throw new BadRequestException(String.format("ERROR: Set id must be provided when adding to a set"));

        return _rummyService.MakeAddSet(gameId, playerId, setId, cards, make);
    }

    //Solitaire
    @GetMapping(value="/test")
    public PilesBaseResponseModel getPiles(@RequestParam String deckId, String cardId) {

        return _rummyService.getPiles(deckId, cardId);
    }

    //Solitaire
    @GetMapping(value="/listpiles")
    public PilesBaseResponseModel getCardsInPile(String gameId, String pileId)
    {
        return _rummyService.getCardsInPile(gameId, pileId);
    }

}

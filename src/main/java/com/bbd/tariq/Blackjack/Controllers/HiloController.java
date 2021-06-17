package com.bbd.tariq.Blackjack.Controllers;

import com.bbd.tariq.Blackjack.Common.Constants;
import com.bbd.tariq.Blackjack.Exceptions.BadRequestException;
import com.bbd.tariq.Blackjack.Exceptions.UnauthorisedException;
import com.bbd.tariq.Blackjack.Interfaces.ICardsApi;
import com.bbd.tariq.Blackjack.Interfaces.IHiloService;
import com.bbd.tariq.Blackjack.Interfaces.IRepoFactory;
import com.bbd.tariq.Blackjack.Models.HiloGame.HiloGameModel;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hilo")
@Component
public class HiloController extends BaseController {

    private IHiloService _hiloService;

    public HiloController(ICardsApi cardsApi, ModelMapper mapper, IRepoFactory repoFactory, IHiloService hiloService) {
        super(cardsApi, mapper, repoFactory, Constants.Hilo.REPO_NAME);

        _hiloService = hiloService;
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/newgame")
    public HiloGameModel newGame(@RequestParam(value = "name", defaultValue = "Anonymous") String name,
        @RequestHeader(name = "Authorization", required = false) String auth) {
        if (auth != null && auth.equals("12345")) {
            return _hiloService.newGame(name);
        } else {
            throw new UnauthorisedException(String.format("ERROR: Authorization is required."));
        }
    }

    @CrossOrigin(origins = "*")
    @PostMapping(value = "/guess")
    public HiloGameModel newGame(@RequestParam(value = "game_id") String game_id,
        @RequestParam(value = "guess", defaultValue = "same") String guess,
        @RequestHeader(name = "Authorization", required = false) String auth) {
        if (auth != null && auth.equals("12345")) {
            if (game_id != null) {                    
                return _hiloService.guess(game_id, guess);
            } else {
                throw new BadRequestException(String.format("ERROR: game_id is required."));
            }
        } else {
            throw new UnauthorisedException(String.format("ERROR: Authorization is required."));
        }
    }
}

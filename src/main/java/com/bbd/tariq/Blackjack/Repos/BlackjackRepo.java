package com.bbd.tariq.Blackjack.Repos;

import com.bbd.tariq.Blackjack.Exceptions.BadRequestException;
import com.bbd.tariq.Blackjack.Models.GameModel;

import java.util.HashMap;
import java.util.LinkedList;

public class BlackjackRepo extends Repo {

    private HashMap<String,GameModel> games = new HashMap<>();
    @Override
    public GameModel Get(String id) {
        var gameInstance = games.get(id);
        if(gameInstance==null){
            throw new BadRequestException("Failed to find game instance with provided game Id!");
        }
        return games.get(id);
    }

    @Override
    public void Insert(GameModel model) {
        games.put(model.gameId,model);
    }

    @Override
    public void Update(GameModel model) {
        games.replace(model.gameId, model);
    }
}

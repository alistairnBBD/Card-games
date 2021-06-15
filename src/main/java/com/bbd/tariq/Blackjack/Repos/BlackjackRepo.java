package com.bbd.tariq.Blackjack.Repos;

import com.bbd.tariq.Blackjack.Models.GameModel;

import java.util.LinkedList;

public class BlackjackRepo extends Repo {

    private LinkedList<GameModel> games = new LinkedList<>();
    @Override
    public GameModel Get(String id) {
        return games.stream().filter(game ->id.equals(game.gameId)).findFirst().orElse(null);
    }

    @Override
    public void Insert(GameModel model) {
        games.add(model);
    }

    @Override
    public void Update(GameModel model) {

    }
}

package com.bbd.tariq.Blackjack.Strategies;

import com.bbd.tariq.Blackjack.Interfaces.IRepoFactory;
import com.bbd.tariq.Blackjack.Repos.BlackjackRepo;
import com.bbd.tariq.Blackjack.Repos.Repo;

public class RepoFactoryStrategy implements IRepoFactory {
    @Override
    public Repo getRepo(String gameName) {

        switch(gameName.toLowerCase()){

            case "blackjack":
                return new BlackjackRepo();



            default:
                return null;
        }
    }
}

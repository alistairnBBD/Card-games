package com.bbd.tariq.Blackjack.Strategies;

import com.bbd.tariq.Blackjack.Common.Constants;
import com.bbd.tariq.Blackjack.Interfaces.IRepoFactory;
import com.bbd.tariq.Blackjack.Repos.BlackjackRepo;
import com.bbd.tariq.Blackjack.Repos.GoFishRepo;
import com.bbd.tariq.Blackjack.Repos.Repo;

public class RepoFactoryStrategy implements IRepoFactory {
    @Override
    public Repo getRepo(String gameName) {

        switch(gameName){

            case Constants.Blackjack.REPO_NAME:
                return new BlackjackRepo();
            case Constants.GoFish.REPO_NAME:
                return new GoFishRepo();

            default:
                return null;
        }
    }
}

package com.bbd.tariq.Blackjack.Strategies;

import com.bbd.tariq.Blackjack.Common.Constants;
import com.bbd.tariq.Blackjack.Interfaces.IRepoFactory;
import com.bbd.tariq.Blackjack.Repos.*;

public class RepoFactoryStrategy implements IRepoFactory {
    @Override
    public Repo getRepo(String gameName) {

        switch(gameName){

            case Constants.Blackjack.REPO_NAME:
                return new BlackjackRepo();
            case Constants.GoFish.REPO_NAME:
                return new GoFishRepo();

            case Constants.Hilo.REPO_NAME:
                return new HiloRepo();

            case Constants.Rummy.REPO_NAME:
                return new RummyRepo();

            default:
                return null;
        }
    }
}

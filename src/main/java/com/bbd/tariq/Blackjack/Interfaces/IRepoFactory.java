package com.bbd.tariq.Blackjack.Interfaces;

import com.bbd.tariq.Blackjack.Repos.Repo;

public interface IRepoFactory {

    public Repo getRepo(String gameName);
}

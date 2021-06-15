package com.bbd.tariq.Blackjack.Repos;


import com.bbd.tariq.Blackjack.Models.GameModel;

public abstract class Repo{

    //Probably setup the connection to the db
    public Repo(){

    }

    public abstract GameModel Get(String id);
    public abstract void Insert(GameModel model);
    public abstract void Update(GameModel model);

}
